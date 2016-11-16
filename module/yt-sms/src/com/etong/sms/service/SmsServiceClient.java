package com.etong.sms.service;

import com.etong.pt.utility.PtResult;

import java.util.List;

/**
 * Created by Administrator on 2015/11/17.
 */
public interface SmsServiceClient {
    PtResult sendMessage(List<String> mobileList, List<String> contentList, String stime, String memberId);
}
