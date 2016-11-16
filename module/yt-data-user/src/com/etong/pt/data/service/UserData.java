/**
 * Created by wunan on 15/12/30.
 */
package com.etong.pt.data.service;

import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.customer.PtCustomerExample;
import com.etong.pt.data.user.PtUserExample;
import com.etong.pt.data.user.detail.UserDetail;
import com.etong.pt.data.user.detail.UserDetailExample;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.data.vehicle.PtVehicleExample;
import com.etong.pt.utility.PtResult;

public interface UserData {
    PtResult getUser(PtUserExample example);
    PtResult getUser(PtUserExample example, String cacheKey);

    PtResult getUserDetail(UserDetailExample example);
    PtResult putUserDetail(UserDetail userDetail);

    PtResult getCustomer(PtCustomerExample example);
    PtResult getCustomer(PtCustomerExample example, String cacheKey);
    PtResult putCustomer(PtCustomer customer);

    PtResult getVehicle(PtVehicleExample example);
    PtResult getVehicle(PtVehicleExample example, String cacheKey);
    PtResult addVehicle(PtVehicle vehicle);
    PtResult putVehicle(PtVehicle vehicle);

    PtResult getUserInfo(PtUserExample example);

    PtResult addUserInfo(UserInfo userInfo);
}
