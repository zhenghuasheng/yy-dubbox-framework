package com.etong.sms.service;

import com.etong.pt.utility.PtResult;
import com.etong.sms.model.Consumer;

public interface ConsumerServer {

    public PtResult addConsumerRequset(Consumer consumer);

    public PtResult getConsumerRequest(String memberName, String memberId);

    public PtResult getConsumerBykey(int id);

    public PtResult getConsumers(Integer start, Integer count);

    public PtResult modifyConsumerRequest(Consumer consumer);


}
