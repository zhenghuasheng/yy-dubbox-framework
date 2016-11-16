package com.etong.sms.service.impl;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.pt.utility.SmsHelper;
import com.etong.sms.data.ConsumerDao;
import com.etong.sms.data.SmsDao;
import com.etong.sms.model.Consumer;
import com.etong.sms.model.Message;
import com.etong.sms.model.MessageTaskModel;
import com.etong.sms.service.MessageServer;
import com.etong.sms.service.SmsServiceClient;
import com.etong.sms.utility.*;
import com.google.code.ssm.Cache;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageServerImpl implements MessageServer {
    private Logger logger = Logger.getLogger(MessageServerImpl.class);
    private SmsDao smsDao;
    private ConsumerDao consumerDao;
    private String namesrvAddr;//消息中心注册地址
    private Cache memcacheClient;


    @PostConstruct
    public void init() {
        commitMessage(null);
    }

    public void setMemcacheClient(Cache memcacheClient) {
        this.memcacheClient = memcacheClient;
    }

    public Cache getMemcacheClient() {
        return memcacheClient;
    }

    public void setConsumerDao(ConsumerDao consumerDao) {
        this.consumerDao = consumerDao;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setSmsDao(SmsDao smsDao) {
        this.smsDao = smsDao;
    }


    @Override
    public PtResult addMessageRequest(Message message) {
        PtResult ptResult = smsDao.addMessageRequest(message);
        return ptResult;

    }

    @Override
    public PtResult getMessageList(String phone, String startDate,
                                   String endDate, String memberId, Integer state, int start, int count) {

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", phone);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("memberId", memberId);
        map.put("state", state);
        map.put("start", start);
        map.put("count", count);
        PtResult ptResult = smsDao.getMessageList(map);
        return ptResult;

    }

    @Override
    public PtResult deleteMessage(Long id) {
        return null;
    }

    /**
     * *
     * 科尚：短信提交发送
     *
     * @param mobileList  手机号码
     * @param contentList 短信内容
     * @param stime       定时发送时间
     * @param memberId    发送端指定ID
     * @return
     */
    @Override
    public PtResult sendMessages(List<String> mobileList, List<String> contentList, String stime, String memberId) {

        PtResult ptResult = null;
        SmsServiceClient smsServiceClient = null;
        String ext = null;
        if (StringUtil.checkContent(contentList)) {//发送短信内容相同时，优先提交发送至云测
            if (mobileList.size() <= SystemConstant.MAXPHONES) {

                logger.info("短信内容相同，已将短信提交至云测发送>>>>>");
                smsServiceClient = new YcClientFactorImpl().creatSmsClient();
                ext = "chanel-YC";
                ptResult = smsServiceClient.sendMessage(mobileList, contentList, stime, memberId);

            } else {
                logger.info("短信内容相同，单批条数超过200，已将短信提交至科尚发送>>>>>>>");
                smsServiceClient = new KesClientFactoryImpl().creatSmsClient();
                ext = "chanel-KS";
                ptResult = smsServiceClient.sendMessage(mobileList, contentList, stime, memberId);
            }

        } else {//发送内容不一致，提交发送至科尚
            logger.info("短信内容不同，已提交至科尚发送>>>>>");
            smsServiceClient = new KesClientFactoryImpl().creatSmsClient();
            ext = "chanel-KS";
            ptResult = smsServiceClient.sendMessage(mobileList, contentList, stime, memberId);
        }

        if (!ptResult.isSucceed()) {
            logger.info("短信发送失败，再次尝试发送，已提交至科尚发送>>>>>");
            smsServiceClient = new KesClientFactoryImpl().creatSmsClient();
            ext = "chanel-KS";
            ptResult = smsServiceClient.sendMessage(mobileList, contentList, stime, memberId);
        }
        int state = 0;
        if (!ptResult.isSucceed()) {
            state = -1;
        } else {
            state = 1;
        }
        for (int i = 0; i < mobileList.size(); i++) {
            Message message = new Message();
            message.setStime(stime);
            message.setContent(contentList.get(i));
            message.setMobile(mobileList.get(i));
            message.setRrid((String) ptResult.getObject());
            message.setState(state);
            message.setExt(ext);
            message.setMemberId(memberId);
            PtResult addResult = addMessageRequest(message);
            if (!addResult.isSucceed()) {
                continue;
            }
        }
        if (state == SendStatus.SEND_FAIL.getCode()) {
            return new PtResult(SmsError.SMS_ERROR_SENDFAIL, null, null);
        } else {
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, "send:" + mobileList.size(), null);
        }

    }

    @Override
    public PtResult commitMessage(MessageTaskModel record) {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("smsConsumer");
        consumer.setNamesrvAddr(namesrvAddr);
        try {
            //订阅PushTopic下Tag为push的消息
            consumer.subscribe(SmsHelper.MESSAGE_TOPIC, SmsHelper.SMS_TAGES);
            //程序第一次启动从消息队列头取数据
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
            consumer.registerMessageListener(new MessageListenerInstance(this));
            consumer.start();
        } catch (Exception e) {
            logger.error("短信消费端启动失败:" + e.getMessage() + "||" + DateUtil.getCurrentDate());
        }
        return null;
    }

    @Override
    public PtResult validateAuth(String clientIP, String memberId) {
        PtResult ptResult = consumerDao.getConsumerRequest(null, memberId);
        if (!ptResult.isSucceed()) {
            logger.info("无memberId为：" + memberId + "的信息，用户无权限操作");
            return ptResult;
        }
        Consumer consumer = ptResult.getObject();
        String memberIp = consumer.getMemberIp();
        if (memberIp == null) {
            logger.info("无memberId为：" + memberId + "的IP绑定信息，用户无权限操作");
            return new PtResult(SmsError.SMS_ERROR_NOAUTHORITY, null, null);
        }
        if (!memberIp.contains(clientIP)) {
            logger.info("无memberId为：" + memberId + "的IP绑定信息，当前IP：||" + clientIP + "||用户无权限操作");
            return new PtResult(SmsError.SMS_ERROR_NOAUTHORITY, null, memberId);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, memberId);
    }

}
