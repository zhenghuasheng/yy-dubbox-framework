/**
 * Created by wunan on 2015/4/21.
 */
package com.etong.pt.utility;

public enum PtCommonError implements PtError {
    PT_ERROR_SUCCESS(0, "成功"),
    PT_ERROR_DB(1, "数据库执行错误"),
    PT_ERROR_INVALID_SERVICE(2, "服务无效"),
    PT_ERROR_JSON_PARSE(3, "JSON解析错误"),
    PT_ERROR_RPC(4, "远程服务调用错误"),
    PT_ERROR_PARAMETER(5, "参数错误"),
    PT_ERROR_SOAP(6, "WebService调用错误"),
    PT_ERROR_NODATA(7, "数据为空"),
    PT_ERROR_USER_NOTEXIST(8, "用户不存在"),
    PT_ERROR_SUBMIT(9, "操作失败"),
    PT_ERROR_PASSWORD(10, "密码错误"),
    PT_ERROR_SMS_SEND(11, "验证码发送失败"),
    PT_ERROR_VERIFY_OVERTIME(12, "验证码超时"),
    PT_ERROR_VERIFY_WRONG(13, "验证码错误"),
    PT_ERROR_LOGON_OVERTIME(14, "登录超时"),
    PT_ERROR_INVALID_TOKEN(15, "令牌已经无效"),
    PT_ERROR_SMS_REDUPLICATED(16, "短信重复发送"),
    PT_ERROR_GEN_TOKEN(17, "登录令牌生成错误"),
    PT_ERROR_VERIFY_TOKEN(18, "令牌验证失败"),
    PT_ERROR_REG_REDUPLICATED(19, "用户已经注册"),
    PT_ERROR_RECORD_REDUPLICATED(20, "记录重复"),
    PT_ERROR_CUST_NOTEXIST(21, "客户记录不存在"),
    PT_ERROR_DB_RESULT(22, "数据库记录更新为空"),
    PT_ERRPR_MESSAGE_TOSTART(23, "短信消费端启动失败"),
    PT_ERRPR_MESSAGE_TOSEND(23, "短信消费端启动失败"),
    PT_ERROR_SERIALIZE(24, "对象序列化失败"),
    PT_ERROR_TIMEOUT(25, "操作超时"),
    PT_ERROR_MEMCACHE(26, "缓存错误"),
    PT_ERROR_NOAUTH(27, "无操作权限"),
    PT_ERROR_IO(28, "IO错误"),
    PT_ERROR_UNKOWN(-1, "未知错误");

    private int code;
    private String message;

    PtCommonError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PtCommonError{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
