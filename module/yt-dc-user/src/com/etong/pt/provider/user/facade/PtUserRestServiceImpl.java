package com.etong.pt.provider.user.facade;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.etong.pt.api.user.PtUserService;
import com.etong.pt.api.user.facade.PtUserRestService;
import com.etong.pt.data.customer.PtCustomer;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.user.detail.UserDetail;
import com.etong.pt.data.vehicle.PtVehicle;
import com.etong.pt.utility.PtResult;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by chenlinyang on 2015/11/10.
 */
@Path("users")
@Produces({ContentType.APPLICATION_JSON_UTF_8})
@Consumes({ContentType.APPLICATION_JSON_UTF_8})
public class PtUserRestServiceImpl implements PtUserRestService {
    private PtUserService ptUserService;

    public void setPtUserService(PtUserService ptUserService) {
        this.ptUserService = ptUserService;
    }

    /**发送手机验证码*/
    public PtResult sendCode(String phone) {
        return null;
    }

    /**验证手机验证码*/
    public PtResult verifyCode(String phone, String verify, Boolean flag) {
        return null;
    }

    /**注册*/
    public PtResult register(PtUser ptUser, PtCustomer ptCustomer, List<PtVehicle> ptVehicles, String verify) {
        return null;
    }

    /**修改用户资料*/
    public PtResult modify(PtUser ptUser, PtCustomer ptCustomer, List<PtVehicle> ptVehicles) {
        return null;
    }

    /**获取用户列表*/
    public PtResult findAll() {
        return null;
    }

    @GET
    @Path("{id : \\d+}")
    public PtResult getById(@PathParam("id")Integer id) {
        return ptUserService.getById(id);
    }

    public PtResult getByPhone(String phone) {
        return null;
    }

    /**获取登录辅助信息*/
    @GET
    @Path("prelogin/{user}/{system}")
    public PtResult getLoginKey(@PathParam("user") String loginName
            , @PathParam("system") String appId) {
        return ptUserService.getLoginKey(loginName, appId);
    }

    /**用户登录*/
    @GET
    @Path("login/{user}/{pwd}/{verify}/{system}")
    public PtResult login(@PathParam("user") String loginName
            , @PathParam("pwd") String pwd
            , @PathParam("verify") String verify
            , @PathParam("system") String appId) {
        return ptUserService.login(loginName, pwd, verify, appId);
    }

    /**手机号、验证码登录*/
    public PtResult login(String phone, String verify, String appId) {
        return null;
    }

    /**用户注销*/
    public PtResult logout(String token) {
        return null;
    }

    /**验证token，并获取用户登录信息*/
    public PtResult checkToken(String token) {
        return null;
    }

    /**重置密码*/
    public PtResult resetPassword(String phone, String pwd, String verify) {
        return null;
    }

    /**修改密码*/
    public PtResult modifyPassword(String token, String oldPwd, String newPwd) {
        return null;
    }

    @GET
    @Path("list/{start}/{limit}")
    @Override
    public PtResult getUser(@PathParam("start") Integer start
            , @PathParam("limit") Integer limit) {
        return ptUserService.getUser(start, limit);
    }

    @GET
    @Path("detail/{start}/{limit}")
    @Override
    public PtResult getUserDetail(@PathParam("start") Integer start
            , @PathParam("limit") Integer limit
            , @QueryParam("condi") String condition) {
        return ptUserService.getUserDetail(start, limit, condition);
    }

    @POST
    @Path("detail")
    @Consumes({ContentType.APPLICATION_JSON_UTF_8})
    @Override
    public PtResult addUserDetail(UserDetail userDetail) {
        return ptUserService.addUserDetail(userDetail);
    }

    @PUT
    @Path("detail")
    @Consumes({ContentType.APPLICATION_JSON_UTF_8})
    @Override
    public PtResult setUserDetail(UserDetail userDetail) {
        return ptUserService.setUserDetail(userDetail);
    }
}
