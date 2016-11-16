package com.etong.sms.utility;

public enum SendStatus {

    SEND_SUCC(1, "发送成功"),
    SEND_FAIL(-1, "发送失败"),
    SEND_SENDING(0, "发送中");

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private SendStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
