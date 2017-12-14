package com.etong.pt.utility;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;

import java.util.UUID;

/**
 * 短信提交消息中心
 * Created by Administrator on 2015/11/3.
 */
public class MessageHelper {

    private  DefaultMQProducer producer;

    public MessageHelper(String serveraddress,String producerName) {
        producer = new DefaultMQProducer(producerName);
        producer.setNamesrvAddr(serveraddress);
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public MessageHelper() {
        producer = new DefaultMQProducer("defaultMQProducer");
        producer.setNamesrvAddr("msg.com:9876");
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public PtResult sendMessageToServer(Object Message,String MessgeTopic, String MessageTags){

        byte[]smsBytes=SerializeHelper.objectToByte(Message);

        Message msg = new Message(MessgeTopic, MessageTags,UUID.randomUUID().toString(), smsBytes);
        String clientIP=producer.getClientIP();
        msg.putUserProperty("clientIP",clientIP);

        SendResult result = null;
        try {
            result = producer.send(msg);
            if (result.getSendStatus().equals(SendStatus.SEND_OK)) {
                return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
            } else {
                return new PtResult(PtCommonError.PT_ERRPR_MESSAGE_TOSEND, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new PtResult(PtCommonError.PT_ERRPR_MESSAGE_TOSEND, e.getClass() + ":" + e.getMessage(), null);
        } finally {
            producer.shutdown();
        }
    }
}
