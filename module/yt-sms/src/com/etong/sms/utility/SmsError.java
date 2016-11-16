package com.etong.sms.utility;

import com.etong.pt.utility.PtError;

public enum SmsError implements PtError {

    SMS_ERROR_MEMBERUNEXIT(1001, "memberId未注册"),
    SMS_ERROR_IPERROR(1002, "IP错误"),
    SMS_ERROR_SENDFAIL(1003, "发送失败"),
    SMS_ERROR_SUBMIT(1004, "提交失败"),
    SMS_ERROR_MEMBERIDERR(1005, "memberId错误或者为空"),
    SMS_ERROR_NOAUTHORITY(1006, "无权限操作");


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


    private SmsError(int code, String message) {
        this.code = code;
        this.message = message;
    }


    private SmsError() {
    }


}
