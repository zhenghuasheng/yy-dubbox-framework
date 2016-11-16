/**
 * Created by wunan on 15-5-18.
 */
package com.etong.pt.utility;

import java.security.MessageDigest;

public class Md5Helper {
    public static String Str2MD5(String input) {
        MessageDigest md5;

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return input;
        }

        byte[] byteArray = input.getBytes();
        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; ++i) {
            int val = ((int) md5Bytes[i]) & 0xff;

            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
