/**
 * Created by wunan on 15-11-24.
 */
package com.etong.dc.auth;

import java.io.Serializable;

public class TokenParam implements Serializable {
    private String issuer = "admin";  //创建者
    private String audience;
    private String subject = "auth";
    private int expireTime = 10;      //10分钟
    private Object extraData;         //扩展数据，需要支持序列化
    private Long userId;              //用于验证权限，不设置权限验证会失败
    private String system;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public Object getExtraData() {
        return extraData;
    }

    public void setExtraData(Object extraData) {
        this.extraData = extraData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Override
    public String toString() {
        return "TokenParam{" +
                "issuer='" + issuer + '\'' +
                ", audience='" + audience + '\'' +
                ", subject='" + subject + '\'' +
                ", expireTime=" + expireTime +
                ", extraData=" + extraData +
                ", userId=" + userId +
                ", system='" + system + '\'' +
                '}';
    }
}
