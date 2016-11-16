package com.etong.pt.utility;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/11/9.
 */
public class SmsHelper {
    public static final String MESSAGE_TOPIC="smsMessage";
    public static final String SMS_TAGES="push";


    public PtResult sendMessageRequest(String phone, String content,String memberId, String stime) {
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setMemberId(memberId);
        if (stime==null){
            stime="";
        }
        smsMessage.setStime(stime);
        smsMessage.setPhone(phone);
        smsMessage.setMsg(content);
        MessageHelper messageHelper=new MessageHelper();
        return messageHelper.sendMessageToServer(smsMessage,MESSAGE_TOPIC,SMS_TAGES);
    }

    public PtResult sendMessageListRequest(List<String> phones, List<String> contents, String stime, String memberId) {
        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setMobiles(phones);
        smsMessage.setContents(contents);
        if (stime==null){
            stime="";
        }
        smsMessage.setStime(stime);
        smsMessage.setMemberId(memberId);
        MessageHelper messageHelper=new MessageHelper();
        return messageHelper.sendMessageToServer(smsMessage,MESSAGE_TOPIC,SMS_TAGES);
    }

    public static  class SmsMessage implements Serializable {
        private String phone;
        private String msg;
        private List<String> mobiles;
        private List<String> contents;
        private String stime;
        private String memberId;


        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }


        public List<String> getMobiles() {
            return mobiles;
        }

        public void setMobiles(List<String> mobiles) {
            this.mobiles = mobiles;
        }

        public List<String> getContents() {
            return contents;
        }

        public void setContents(List<String> contents) {
            this.contents = contents;
        }

        public String getStime() {
            return stime;
        }

        public void setStime(String stime) {
            this.stime = stime;
        }

        public String getMemberId() {
            return memberId;
        }

        public void setMemberId(String memberId) {
            this.memberId = memberId;
        }

        public SmsMessage(String phone, String msg, List<String> mobiles, List<String> contents, String stime, String memberId) {
            this.phone = phone;
            this.msg = msg;
            this.mobiles = mobiles;
            this.contents = contents;
            this.stime = stime;
            this.memberId = memberId;
        }

        public SmsMessage() {

        }
        @Override
        public String toString() {
            return "SmsMessage{" +
                    "phone='" + phone + '\'' +
                    ", msg='" + msg + '\'' +
                    ", mobiles=" + mobiles +
                    ", contents=" + contents +
                    ", stime='" + stime + '\'' +
                    ", memberId='" + memberId + '\'' +
                    '}';
        }

    }
}
