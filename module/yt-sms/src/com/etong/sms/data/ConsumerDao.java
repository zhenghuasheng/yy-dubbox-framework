package com.etong.sms.data;

import com.etong.pt.utility.PtResult;
import com.etong.sms.model.Consumer;

/**
 * Created by Administrator on 2015/11/13.
 */
public interface ConsumerDao {
    public PtResult addConsumerRequset(Consumer consumer);

    public PtResult getConsumerRequest(String memberName, String memberId);

    public PtResult getConsumerBykey(int id);

    public PtResult getConsumers(int start, int count);

    public PtResult updateConsumerRequest(Consumer consumer);
}
