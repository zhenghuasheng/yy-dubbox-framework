package com.etong.sms.model;

public class MessageTaskModel {
    private String jsonString;
    private String stime;
    private String memberId;

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
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

    public MessageTaskModel(String jsonString, String stime, String memberId) {
        super();
        this.jsonString = jsonString;
        this.stime = stime;
        this.memberId = memberId;
    }

    public MessageTaskModel() {
        super();
    }


}
