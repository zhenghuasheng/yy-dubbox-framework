/**
 * Created by wunan on 16-1-29.
 */
package com.etong.captcha.dao;

import com.google.code.ssm.api.CacheKeyMethod;

import java.io.Serializable;

public class Captcha implements Serializable {
    private String sequence;
    private String code;
    private long createTime;
    private String system;
    private long verifyTime;

    @CacheKeyMethod
    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public long getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(long verifyTime) {
        this.verifyTime = verifyTime;
    }
}
