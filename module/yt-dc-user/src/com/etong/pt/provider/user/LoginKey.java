/**
 * Created by wunan on 15-5-18.
 */
package com.etong.pt.provider.user;

import java.io.Serializable;

public class LoginKey implements Serializable {
    private boolean verify;   //是否需要输入验证码
    private String salt;      //盐值
    private String extraKey;  //附加key，用户密码二次哈希

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getExtraKey() {
        return extraKey;
    }

    public void setExtraKey(String extraKey) {
        this.extraKey = extraKey;
    }
}
