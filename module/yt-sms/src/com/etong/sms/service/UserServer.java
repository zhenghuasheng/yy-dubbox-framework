package com.etong.sms.service;


import com.etong.sms.model.User;
import com.etong.sms.utility.SmsResult;

public interface UserServer {
    public SmsResult insertUser(User user);

    public SmsResult getSingleUser(int id);

    public SmsResult getUserList(int start, int count);

}
