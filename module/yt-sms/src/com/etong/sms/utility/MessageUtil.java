package com.etong.sms.utility;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * ******************************
 * <p/>
 * 短信发送接口调用类
 *
 * @author YangJin
 *         <p/>
 *         ******************************
 */
public class MessageUtil {

    String url = "http://api.sms.testin.cn/sms";
    String strUID = "";
    String strPWD = "";

    public static String transmessage(String requrl, String paras) {
        String ret = null;
        try {

            URL url = new URL(requrl);

            HttpURLConnection httpConn = (HttpURLConnection) url
                    .openConnection();
            httpConn.setConnectTimeout(30000);
            httpConn.setReadTimeout(30000);
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            httpConn.setRequestMethod("POST");
            DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());

            String reqstr = paras;

            System.out.println("url:" + requrl);
            System.out.println("reqstr:" + reqstr);
            out.write(reqstr.getBytes("UTF-8"));

            out.flush();
            out.close();
            InputStream stream = httpConn.getInputStream();

            DataInputStream in = new DataInputStream(stream);
            byte[] bin = null;
            byte[] inc = new byte[1024];
            int datelength = 0;
            int insize = 0;
            System.out.println("ret insize: " + insize);
            while ((insize = in.read(inc)) != -1) {
                int oldlength = datelength;
                datelength += insize;
                byte[] oldbin = new byte[datelength];
                for (int i = 0; i < oldlength; i++)
                    oldbin[i] = bin[i];
                for (int i = oldlength; i < datelength; i++)
                    oldbin[i] = inc[i - oldlength];
                bin = oldbin;
            }
            ret = new String(bin, "UTF8");
            in.close();
            System.out.println("ret is: " + ret);
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return ret;
    }


    //接收手机回复的短信（要单独申请开通）
//    private String recvMO() {
//        String result;
//
//        long ltime = System.currentTimeMillis();
//        try {
//            JSONObject jsonObj = new JSONObject();
//
//            jsonObj.put("op", "Sms.mo");
//
//            jsonObj.put("apiKey", strUID);
//            jsonObj.put("ts", ltime);
//
//            jsonObj.put("sig", getSig(jsonObj, strPWD));
//
//            result = transmessage(URL, jsonObj.toString());
//
//            JSONObject jsonReq = new JSONObject(result);
//
//            //	System.out.println(jsonReq.get("code").toString());
//
//            if (jsonReq.get("code").toString().equals("1000")) {
//                System.out.println((new java.text.SimpleDateFormat(
//                        "yyyy-MM-dd HH:mm:ss")).format(new Date())
//                        + "回复短信:"
//                        + result);
//
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "";
//
//    }

    //接收发送的短信的状态报告，通过返回结果里的teskid和发送的参数里的teskid关联，群发短信还有关联手机号码，确定手机是否收到了短信
//    private String recvRPT() {
//        String result;
//        long ltime = System.currentTimeMillis();
//        try {
//            JSONObject jsonObj = new JSONObject();
//
//            jsonObj.put("op", "Sms.status");
//
//            jsonObj.put("apiKey", strUID);
//            jsonObj.put("ts", ltime);
//
//            jsonObj.put("sig", getSig(jsonObj, strPWD));
//
//            result = transmessage(url, jsonObj.toString());
//
//            JSONObject jsonReq = JSON.parseObject(result);
//
//            //	System.out.println(jsonReq.get("code").toString());
//
//            if (jsonReq.get("code").toString().equals("1000")) {
//                System.out.println((new java.text.SimpleDateFormat(
//                        "yyyy-MM-dd HH:mm:ss")).format(new Date())
//                        + "报告回执:"
//                        + result);
//
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "";
//    }

    //查询账号的余额。
//    private String checkBalance() {
//        String result;
//
//        long ltime = System.currentTimeMillis();
//        try {
//            JSONObject jsonObj = new JSONObject();
//
//            jsonObj.put("op", "Sms.account");
//
//            jsonObj.put("apiKey", strUID);
//            jsonObj.put("ts", ltime);
//
//            jsonObj.put("sig", getSig(jsonObj, strPWD));
//
//            result = transmessage(URL, jsonObj.toString());
//
//            JSONObject jsonReq = new JSONObject(result);
//
//            //	System.out.println(jsonReq.get("code").toString());
//
//            if (jsonReq.get("code").toString().equals("1000")) {
//                System.out.println((new java.text.SimpleDateFormat(
//                        "yyyy-MM-dd HH:mm:ss")).format(new Date())
//                        + "账号余额:"
//                        + jsonReq.get("balance"));
//
//            }
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        return "";
//    }

//    public static void main(String[] args) throws Exception {
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("op", "Sms.send");
//        jsonObj.put("apiKey", "0b20f0c730ec76e974fb533f00811f19");
//        jsonObj.put("ts", System.currentTimeMillis());
//        jsonObj.put("phone", "137XXXXXXXX");
//        jsonObj.put("templateId", "1002");
//        jsonObj.put("content", "5643");
//        jsonObj.put("taskId", System.currentTimeMillis() );//不超过64位长度的唯一字符串，通过和recvRPT获取的结果里的teskid关联，确定发送的信息是否收到。
//        jsonObj.put("extNum", "" );
//        jsonObj.put("sig", getSig(jsonObj, "3B1F9AAAAA2973E8"));
//
//        String result = MessageUtil.transmessage(url, jsonObj.toString());
//        System.out.println(result);
//
//        MessageUtil.recvMO(); //接受回复（循环调用，有回复返回结果list，没有list返回null）
//       // ret is: {"op":"Sms.mo","list":[{"content":"gpo","extNum":"322123","phone":"15821030000"}],"code":1000,"msg":"成功"}
//
//         MessageUtil.recvRPT();//接收报告  //接受回复（循环调用，有回复返回结果list，没有list返回null）
//         //ret is: {"op":"Sms.status","list":[{"taskId":"1446429441789","phone":"139107*****","status":1,"descr":"DELIVRD"},{"taskId":"1446009231400","phone":"139107*****","status":1,"descr":"DELIVRD"}],"code":1000,"msg":"成功"}
//
//          MessageUtil.checkBalance(); //查余额
//        //  ret is: {"balance":1194,"op":"Sms.account","code":1000,"msg":"成功"}
//    }

    /**
     * 获取md5加密字符串 32位长
     *
     * @param
     * @return String 密文
     */
    private final static String MD5(String s) {
        char hexdigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        try {
            byte[] strTemp = s.getBytes("utf-8");
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexdigits[byte0 >>> 4 & 0xf];
                str[k++] = hexdigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取Testin平台数字签名
     *
     * @param reqjson   请求的json
     * @param secretkey Testin平台密钥
     * @return String
     */
    public static String getSig(JSONObject reqjson, String secretkey) {
        String result = null;

        if (reqjson == null || secretkey == null) {
            return result;
        }

        try {
            StringBuffer sb = new StringBuffer();
            Iterator itKeys = reqjson.keys();
            TreeSet setKeys = new TreeSet();
            while (itKeys.hasNext()) {
                setKeys.add(itKeys.next());
            }
            Iterator sortedIt = setKeys.iterator();
            for (Iterator itKey = sortedIt; sortedIt.hasNext(); ) {
                String key = (String) itKey.next();
                if ("sig".equals(key)) {
                    continue;
                }
                Object obj = reqjson.get(key);

                sb.append(key);
                sb.append("=");

                if (obj instanceof JSONObject || obj instanceof JSONArray) {
                    String value = getSortJsonStr(obj);
                    sb.append(value);
                } else {
                    String value = reqjson.getString(key);
                    sb.append(value);
                }

            }
            sb.append(secretkey);
            result = MD5(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 递归获取排过序的json信息
     *
     * @param objJson json排序
     * @return String
     */
    private static String getSortJsonStr(Object objJson) {
        StringBuffer result = new StringBuffer();
        try {
            if (objJson instanceof JSONArray) {
                result.append("[");
                for (int i = 0; i < ((JSONArray) objJson).length(); i++) {
                    Object arrayMember = ((JSONArray) objJson).get(i);
                    if (i > 0) {
                        result.append(",");
                    }

                    if (arrayMember instanceof JSONObject || arrayMember instanceof JSONArray) {
                        result.append(getSortJsonStr(arrayMember));
                    } else if (arrayMember instanceof String) {
                        result.append("\"" + arrayMember + "\"");
                    } else {
                        result.append(arrayMember);
                    }
                }
                result.append("]");
            } else if (objJson instanceof JSONObject) {
                result.append("{");
                Iterator itKeys = ((JSONObject) objJson).keys();
                TreeSet setKeys = new TreeSet();
                while (itKeys.hasNext()) {
                    setKeys.add(itKeys.next());
                }
                Iterator sortedIt = setKeys.iterator();
                for (Iterator it = sortedIt; sortedIt.hasNext(); ) {
                    String key = (String) it.next();
                    Object object = ((JSONObject) objJson).get(key);

                    if (object == null) {
                        continue;
                    }

                    if (result.length() > 1) {
                        result.append(",");
                    }

                    if (object instanceof String) {
                        result.append("\"" + key + "\":");
                        result.append("\"" + object + "\"");
                    } else if (object instanceof Long
                            || object instanceof Integer) {
                        result.append("\"" + key + "\":");
                        result.append(object);
                    } else if (object instanceof JSONObject
                            || object instanceof JSONArray) {
                        result.append("\"" + key + "\":");
                        result.append(getSortJsonStr(object));
                    }
                }
                result.append("}");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
