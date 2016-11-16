package com.etong.sms.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String str = sdf.format(new Date());
        return str;
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String DateLayout(String str) {
        String strNew = null;
        try {
            Date date = new SimpleDateFormat("yyyy-M-d").parse(str);
            strNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strNew;
    }
}
