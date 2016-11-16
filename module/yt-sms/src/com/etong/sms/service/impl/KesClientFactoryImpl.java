package com.etong.sms.service.impl;

import com.etong.sms.service.SmsClientFactory;
import com.etong.sms.service.SmsServiceClient;

/**
 * Created by Administrator on 2015/11/17.
 */
public class KesClientFactoryImpl implements SmsClientFactory {
    @Override
    public SmsServiceClient creatSmsClient() {
        return new KesmsClientImpl();
    }
}
