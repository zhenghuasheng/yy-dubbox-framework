/**
 * Created by wunan on 15-5-15.
 */
package com.etong.pt.provider.user;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.etong.dc.auth.AuthService;
import com.etong.filter.AuthContext;
import com.etong.pt.api.customer.PtCustomerService;
import com.etong.pt.api.session.PtSessionService;
import com.etong.pt.api.user.PtUserService;
import com.etong.pt.api.vehicle.PtVehicleService;
import com.etong.pt.dao.PtUserDao;
import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.user.PtUserExample;
import com.etong.pt.data.user.detail.UserDetail;
import com.etong.pt.data.user.detail.UserDetailExample;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.provider.user.constants.Validator;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;

import java.util.List;

public class PtUserServiceImpl implements PtUserService {
    private PtUserDao ptUserDao;
    private PtCustomerService ptCustomerService;
    private PtVehicleService ptVehicleService;
    private AuthService authService;

    public void setSmsServerUrl(String smsServerUrl) {
        VerifyManager.getInstance().setServerUrl(smsServerUrl);
    }

    public void setPtUserDao(PtUserDao ptUserDao) {
        this.ptUserDao = ptUserDao;
        VerifyManager.getInstance().setPtUserDao(ptUserDao);
    }

    public void setPtSessionService(PtSessionService ptSessionService) {
        VerifyManager.getInstance().setPtSessionService(ptSessionService);
    }

    public void setPtCustomerService(PtCustomerService ptCustomerService) {
        this.ptCustomerService = ptCustomerService;
    }

    public void setPtVehicleService(PtVehicleService ptVehicleService) {
        this.ptVehicleService = ptVehicleService;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
        VerifyManager.getInstance().setAuthService(authService);
    }

    @Override
    public PtResult register(PtUser ptUser, PtCustomer ptCustomer, List<PtVehicle> ptVehicles, String verify) {
        PtResult ptResult = VerifyManager.getInstance()
                .verifyCode(ptUser.getF_phone(), verify);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        // 密码需要使用MD5值，计算方式MD5(密码+盐值)
        //ptUser.setF_password(Md5Helper.Str2MD5(ptUser.getF_password() + ptUser.getF_salt()));
        ptResult = ptUserDao.add(ptUser);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        ptCustomer.setF_mid(ptResult.<Integer>getObject());
        ptResult = ptCustomerService.add(ptCustomer);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        for(PtVehicle ptVehicle : ptVehicles) {
            ptVehicle.setF_ciid(ptResult.<Long>getObject());
        }
        ptResult = ptVehicleService.batch(ptVehicles);

        return ptResult;
    }

    @Override
    public PtResult modify(PtUser ptUser, PtCustomer ptCustomer, List<PtVehicle> ptVehicles) {
        PtResult ptResult = null;

        if(ptUser != null) {
            ptResult = ptUserDao.update(ptUser);
            if (!ptResult.isSucceed()) {
                return ptResult;
            }
        }

        if(ptCustomer != null) {
            ptResult = ptCustomerService.update(ptCustomer);
            if (!ptResult.isSucceed()) {
                return ptResult;
            }
        }

        if(ptVehicles != null && !ptVehicles.isEmpty()) {
            ptResult = ptVehicleService.batch(ptVehicles);
            if (!ptResult.isSucceed()) {
                return ptResult;
            }
        }

        return ptResult;
    }

    @Override
    public PtResult findAll() {
        PtResult ptResult = ptUserDao.findAll();
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<PtUser> ptUsers = ptResult.<List>getObject();
        JSONArray jsonArray = new JSONArray(ptUsers.size());
        JSONObject jsonObject = null;
        for (PtUser ptUser : ptUsers) {
            jsonObject = new JSONObject();
            jsonObject.put("userId", ptUser.getF_mid());
            jsonObject.put("userName", ptUser.getF_name());
            jsonObject.put("phone", ptUser.getF_phone());
            jsonObject.put("email", ptUser.getF_email());

            ptResult = ptCustomerService.getByUserId(ptUser.getF_mid());
            if (!ptResult.isSucceed()) {
                return ptResult;
            }

            PtCustomer ptCustomer = ptResult.<PtCustomer>getObject();
            jsonObject.put("name", ptCustomer.getF_name());
            jsonObject.put("idcard", ptCustomer.getF_idcard());

            ptResult = ptVehicleService.getListByCustId(ptCustomer.getF_ciid());
            if (ptResult.isSucceed()) {
                List<PtVehicle> ptVehicles = ptResult.<List>getObject();
                JSONArray vehicleJsonArray = new JSONArray(ptVehicles.size());
                JSONObject vehicleJsonObject = null;
                for(PtVehicle ptVehicle : ptVehicles) {
                    vehicleJsonObject = new JSONObject();
                    vehicleJsonObject.put("plateNumber", ptVehicle.getF_plate_number());
                    vehicleJsonArray.add(vehicleJsonObject);
                }
                jsonObject.put("vehicle", vehicleJsonArray);
            }
            jsonArray.add(jsonObject);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonArray);
    }

    @Override
    public PtResult getById(Integer id) {
        PtResult ptResult = ptUserDao.getById(id);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        PtUser ptUser = ptResult.<PtUser>getObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", ptUser.getF_mid());
        jsonObject.put("userName", ptUser.getF_name());
        jsonObject.put("phone", ptUser.getF_phone());
        jsonObject.put("email", ptUser.getF_email());

        ptResult = ptCustomerService.getByUserId(ptUser.getF_mid());
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        PtCustomer ptCustomer = ptResult.<PtCustomer>getObject();
        jsonObject.put("name", ptCustomer.getF_name());
        jsonObject.put("idcard", ptCustomer.getF_idcard());

        ptResult = ptVehicleService.getListByCustId(ptCustomer.getF_ciid());
        if (ptResult.isSucceed()) {
            List<PtVehicle> ptVehicles = ptResult.<List>getObject();
            JSONArray vehicleJsonArray = new JSONArray(ptVehicles.size());
            JSONObject vehicleJsonObject = null;
            for(PtVehicle ptVehicle : ptVehicles) {
                vehicleJsonObject = new JSONObject();
                vehicleJsonObject.put("plateNumber", ptVehicle.getF_plate_number());
                vehicleJsonArray.add(vehicleJsonObject);
            }
            jsonObject.put("vehicle", vehicleJsonArray);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
    }

    @Override
    public PtResult getByPhone(String phone) {
        PtResult ptResult = ptUserDao.getByPhone(phone);
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        PtUser ptUser = ptResult.<PtUser>getObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", ptUser.getF_mid());
        jsonObject.put("userName", ptUser.getF_name());
        jsonObject.put("phone", ptUser.getF_phone());
        jsonObject.put("email", ptUser.getF_email());

        ptResult = ptCustomerService.getByUserId(ptUser.getF_mid());
        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        PtCustomer ptCustomer = ptResult.<PtCustomer>getObject();
        jsonObject.put("name", ptCustomer.getF_name());
        jsonObject.put("idcard", ptCustomer.getF_idcard());

        ptResult = ptVehicleService.getListByCustId(ptCustomer.getF_ciid());
        if (ptResult.isSucceed()) {
            List<PtVehicle> ptVehicles = ptResult.<List>getObject();
            JSONArray vehicleJsonArray = new JSONArray(ptVehicles.size());
            JSONObject vehicleJsonObject = null;
            for(PtVehicle ptVehicle : ptVehicles) {
                vehicleJsonObject = new JSONObject();
                vehicleJsonObject.put("plateNumber", ptVehicle.getF_plate_number());
                vehicleJsonArray.add(vehicleJsonObject);
            }
            jsonObject.put("vehicle", vehicleJsonArray);
        }
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, jsonObject);
    }

    @Override
    public PtResult getLoginKey(String loginName, String appId) {
        return VerifyManager.getInstance().getLoginKey(loginName, appId);
    }

    @Override
    public PtResult login(String loginName, String pwd, String verify, String appId) {
        return VerifyManager.getInstance().doLogin(loginName, pwd, verify, appId);
    }

    @Override
    public PtResult login(String phone, String verify, String appId) {
        return VerifyManager.getInstance().doLogin(phone, verify, appId);
    }

    @Override
    public PtResult logout(String token) {
        return VerifyManager.getInstance().destroyToken(token);
    }

    @Override
    public PtResult checkToken(String token) {
        return VerifyManager.getInstance().verifyToken(token);
    }

    @Override
    public PtResult resetPassword(String phone, String pwd, String verify) {
        return VerifyManager.getInstance().resetPassword(phone, pwd, verify);
    }

    @Override
    public PtResult modifyPassword(String token, String oldPwd, String newPwd) {
        return VerifyManager.getInstance().modifyPassword(token, oldPwd, newPwd);
    }

    @Override
    public PtResult getUser(Integer start, Integer limit) {
        PtUserExample example = new PtUserExample();
        example.setLimitClause(start, limit);
        return ptUserDao.getUser(example);
    }

    @Override
    public PtResult getUserDetail(Integer start, Integer limit, String condition) {
        UserDetailExample example = new UserDetailExample();
        String system = AuthContext.getInstance().getSystem();
        UserDetailExample.Criteria criteria = example.or();

        if (!AuthContext.SYSTEM_ADMIN.equals(system)) {
            criteria.andStidEqualTo(system);
        }

        if ((condition != null) && !condition.isEmpty()) {
            if (Validator.isNumber(condition)) {
                criteria.andPhoneLike("%" + condition + "%");
            } else {
                criteria.andNameLike("%" + condition + "%");
            }
        }

        example.setLimitClause(start, limit);
        return ptUserDao.getUserDetail(example);
    }

    @Override
    public PtResult addUserDetail(UserDetail userDetail) {
        if ((userDetail.getLoginid() == null)
                || (userDetail.getUsername() == null)
                || (userDetail.getPassword() == null)
                || (userDetail.getSalt() == null)) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "缺少添加用户的必要参数", null);
        }

        return ptUserDao.putUserDetail(userDetail);
    }

    @Override
    public PtResult setUserDetail(UserDetail userDetail) {
        if (userDetail.getMid() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "缺少添加用户的必要参数", null);
        }

        return ptUserDao.putUserDetail(userDetail);
    }
}
