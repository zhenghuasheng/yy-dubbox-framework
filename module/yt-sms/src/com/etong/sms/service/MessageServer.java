package com.etong.sms.service;


import com.etong.pt.utility.PtResult;
import com.etong.sms.model.Message;
import com.etong.sms.model.MessageTaskModel;
import com.etong.sms.utility.SmsResult;

import java.util.List;

public interface MessageServer {

    public PtResult addMessageRequest(Message message);

    /**
     * Map<String, Object>map传入数据层*
     */
    public PtResult getMessageList(String phone, String startDate, String endDate, String memberId, Integer state, int start, int count);

    public PtResult deleteMessage(Long id);

    //科尚通道提交短信
    public PtResult sendMessages(List<String> mobileList, List<String> contentList, String stime, String memberId);

    public PtResult commitMessage(MessageTaskModel record);

    //验证用户memberid和ip
    public PtResult validateAuth(String clientIP, String memberId);

}
