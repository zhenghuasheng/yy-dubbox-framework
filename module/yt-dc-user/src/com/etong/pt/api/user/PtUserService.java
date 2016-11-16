/**
 * Created by wunan on 15-5-15.
 */

package com.etong.pt.api.user;

import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.user.detail.UserDetail;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.utility.PtResult;

import java.util.List;

public interface PtUserService {
    /**注册*/
    PtResult register(PtUser ptUser, PtCustomer ptCustomer, List<PtVehicle> ptVehicles, String verify);
    /**修改用户资料*/
    PtResult modify(PtUser ptUser, PtCustomer ptCustomer, List<PtVehicle> ptVehicles);
    /**获取用户列表*/
    PtResult findAll();
    PtResult getById(Integer id);
    PtResult getByPhone(String phone);
    /**获取登录辅助信息*/
    PtResult getLoginKey(String loginName, String appId);
    /**用户登录*/
    PtResult login(String loginName, String pwd, String verify, String appId);
    /**手机号、验证码登录*/
    PtResult login(String phone, String verify, String appId);
    /**用户注销*/
    PtResult logout(String token);
    /**验证token，并获取用户登录信息*/
    PtResult checkToken(String token);
    /**重置密码*/
    PtResult resetPassword(String phone, String pwd, String verify);
    /**修改密码*/
    PtResult modifyPassword(String token, String oldPwd, String newPwd);

    PtResult getUser(Integer start, Integer limit);

    PtResult getUserDetail(Integer start, Integer limit, String condition);

    PtResult addUserDetail(UserDetail userDetail);
    PtResult setUserDetail(UserDetail userDetail);
}
