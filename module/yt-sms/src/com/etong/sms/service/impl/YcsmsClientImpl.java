package com.etong.sms.service.impl;

import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.sms.service.SmsServiceClient;
import com.etong.sms.utility.*;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2015/11/17.
 */
public class YcsmsClientImpl implements SmsServiceClient {
    private Logger logger = Logger.getLogger(YcsmsClientImpl.class);
    public static final String URL = "http://api.sms.testin.cn/sms";
    public static final String APIKEY = "db69a33ceeea2d1404ca9edee903d334";
    public static final String SECRETKEY = "40307A519C262A31";
    public static  String TEMPLATEID1 = "1149";//1195, 营销通道 1149 普通

    public YcsmsClientImpl() {
        String path = System.getProperty("user.dir");
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path + "/config/sms-config.properties");
            Properties prop = new Properties();
            prop.load(fileInputStream);
            TEMPLATEID1=prop.getProperty("yc-chanel");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public PtResult sendMessage(List<String> mobileList, List<String> contentList, String stime, String memberId) {

        String mobileString = StringUtil.listString(mobileList, SystemConstant.SPLIT);
        String content = contentList.get(0);
        String rrid = DateUtil.getDateStr();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("op", "Sms.send");
            jsonObject.put("apiKey", APIKEY);
            jsonObject.put("ts", new Date().getTime());
            jsonObject.put("templateId", TEMPLATEID1);
            jsonObject.put("phone", mobileString);
            jsonObject.put("content", content);
            jsonObject.put("taskId", rrid);
            jsonObject.put("sig", MessageUtil.getSig(jsonObject, SECRETKEY));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            String result = MessageUtil.transmessage(URL, jsonObject.toString());

            JSONObject jb = new JSONObject(result);
            if (!"1000".equals(jb.getString("code"))) {
                logger.info("短信发送失败，错误代码：" + jb.get("code"));
                return new PtResult(SmsError.SMS_ERROR_SENDFAIL, null, null);
            }
            return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, rrid);
        } catch (JSONException e) {
            logger.error(e.getMessage());
            return new PtResult(PtCommonError.PT_ERROR_JSON_PARSE, e.getMessage(), null);
        }

    }
}
