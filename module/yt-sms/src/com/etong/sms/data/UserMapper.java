package com.etong.sms.data;

import java.util.List;
import java.util.Map;

import com.etong.sms.model.User;

public interface UserMapper {
    public int insertUser(User user);

    public User getSingleUser(int id);

    public List<User> getUserList(Map<String, Object> map);

}
