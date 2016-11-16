package com.etong.pt.provider.user.constants;

/**
 * Created by chenlinyang on 2015/11/11.
 */
public class Enumconstant {
    public static enum ClientType {
        SendMsg(0, "发送短信"),
        Login(1, "登录");

        private int value;
        private String desc;

        ClientType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return desc;
        }
    }

}
