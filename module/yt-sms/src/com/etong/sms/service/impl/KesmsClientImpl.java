package com.etong.sms.service.impl;

import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.etong.sms.service.SmsServiceClient;
import com.etong.sms.utility.*;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public class KesmsClientImpl implements SmsServiceClient {
    private Logger logger = Logger.getLogger(KesmsClientImpl.class);

    @Override
    public PtResult sendMessage(List<String> mobileList, List<String> contentList, String stime, String memberId) {
        String rrid = DateUtil.getDateStr();
        if (stime == null) {
            stime = "";
        }
        for (int i = 0; i < contentList.size(); i++) {
            String newcontent = contentList.get(i) + SystemConstant.MARK;
            contentList.set(i, newcontent);
        }
        String mobileString = StringUtil.listString(mobileList, SystemConstant.SPLIT);
        /******************************************************** 短信发送 ****************************************************************/
        ksClient ksClient = null;
        try {
            ksClient = new ksClient(SystemConstant.SN, SystemConstant.PWD);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String result = null;
        if (StringUtil.checkContent(contentList)) {// 内容相同
            String messageContent = contentList.get(0);
            result = ksClient.mt(mobileString, messageContent, SystemConstant.EXT, stime, rrid);
            logger.info("短信使用科尚通道mt方式提交发送");
        } else {
            String messageContent = StringUtil.listCharSetString(contentList, SystemConstant.SPLIT, SystemConstant.CHARSET);
            result = ksClient.gxmt(mobileString, messageContent, SystemConstant.EXT, stime, rrid);
            logger.info("短信使用科尚通道gxmt方式提交发送");
        }

        if (null == result || result.startsWith("-") || result.isEmpty()) {
            return new PtResult(SmsError.SMS_ERROR_SENDFAIL, null, rrid);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, "send:" + mobileList.size(), rrid);
    }
}
