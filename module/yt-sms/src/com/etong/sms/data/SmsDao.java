package com.etong.sms.data;

import com.etong.pt.utility.PtResult;
import com.etong.sms.model.Message;
import com.etong.sms.utility.SmsResult;

import java.util.Map;

/**
 * Created by Administrator on 2015/11/2.
 */
public interface SmsDao {
    public PtResult addMessageRequest(Message message);

    /**
     * String phone,String startDate,String endDate,String memberId,int state
     */
    public PtResult getMessageList(Map<String, Object> map);

    public PtResult deleteMessage(Long id);
}
