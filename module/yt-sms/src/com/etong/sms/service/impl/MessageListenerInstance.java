package com.etong.sms.service.impl;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.utility.SerializeHelper;
import com.etong.pt.utility.SmsHelper;
import com.etong.sms.utility.DateUtil;
import com.google.code.ssm.api.format.SerializationType;
import com.google.code.ssm.providers.CacheException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2015/11/5.
 */
public class MessageListenerInstance implements MessageListenerConcurrently {
    private Logger logger = Logger.getLogger(MessageListenerInstance.class);
    private MessageServerImpl messageServerImpl;


    public MessageListenerInstance(MessageServerImpl messageServerImpl) {
        this.messageServerImpl = messageServerImpl;
    }


    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

        for (MessageExt msg : list) {
            String key = msg.getKeys();
            PtResult checkResult = this.getfromCache(key);
            if (checkResult.isSucceed()) {
                continue;
            }

            byte[] sms = msg.getBody();
            if (sms.length < 1) {
                continue;
            }
            //消费消息体
            SmsHelper.SmsMessage smsMessage = null;
            try {
                smsMessage = SerializeHelper.byteToObject(sms);
            } catch (Exception e) {
                logger.error("短信实体反序列化失败->" + e.getMessage());
            }
            String clientIP = msg.getUserProperty("clientIP");
            String userId = smsMessage.getMemberId();//根据memberID判断权限
            PtResult memberResult = messageServerImpl.validateAuth(clientIP, userId);
            if (!memberResult.isSucceed()) {
                logger.info(DateUtil.getCurrentDate() + "->|当前消息因用户无权限,已被忽略" + smsMessage.toString());
                continue;
            }
            //消息体提交通道发送
            PtResult ptResult = null;
            if (smsMessage.getMobiles() == null && smsMessage.getContents() == null) {//短信单发
                logger.info("短信单发-->");
                String memberId = smsMessage.getMemberId();
                String stime = smsMessage.getStime();
                String phone = smsMessage.getPhone();
                String content = smsMessage.getMsg();
                List<String> phoneList = new ArrayList<String>();
                phoneList.add(phone);
                List<String> contentList = new ArrayList<String>();
                contentList.add(content);
                ptResult = messageServerImpl.sendMessages(phoneList, contentList, stime, memberId);
            } else {//短信群发
                logger.info(DateUtil.getCurrentDate() + "短信群发==>>>");
                ptResult = messageServerImpl.sendMessages(smsMessage.getMobiles(), smsMessage.getContents(), smsMessage.getStime(), smsMessage.getMemberId());
            }
            if (!ptResult.isSucceed()) {//消费结果判定
                logger.info(DateUtil.getCurrentDate() + "||" + ptResult.toString() + "短信消费失败,稍后将重新消费||" + smsMessage.toString());
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            } else {
                logger.info(DateUtil.getCurrentDate() + "短信已消费，成功发送||" + smsMessage.toString());
                try {
                    messageServerImpl.getMemcacheClient().set(key, 3600, key, SerializationType.PROVIDER);
                } catch (TimeoutException e) {
                    logger.info("连接缓存超时,添加缓存记录失败|" + e.getMessage());
                } catch (CacheException e) {
                    logger.info(",添加缓存记录失败|" + e.getMessage());
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }

    public PtResult getfromCache(String key) {
        try {
            Object object = messageServerImpl.getMemcacheClient().get(key, SerializationType.PROVIDER);
            if (object == null) {
                return new PtResult(PtCommonError.PT_ERROR_NODATA, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, object);
        } catch (TimeoutException e) {
            logger.error("访问缓存超时|" + e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_MEMCACHE, e.getMessage(), null);
        } catch (CacheException e) {
            return new PtResult(PtCommonError.PT_ERROR_MEMCACHE, e.getMessage(), null);
        }
    }
}
