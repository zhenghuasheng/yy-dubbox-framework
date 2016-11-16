package com.etong.sms.data;

import java.util.List;
import java.util.Map;

import com.etong.sms.model.Consumer;

public interface ConsumerMapper {

    public int addConsumerRequset(Consumer consumer);

    public Consumer getConsumerRequest(Map<String, Object> map);

    public Consumer getConsumerBykey(int id);

    public List<Consumer> getConsumers(Map<String, Object> map);

    public int updateConsumerRequest(Consumer consumer);

}
