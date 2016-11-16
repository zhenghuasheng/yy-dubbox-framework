/**
 * Created by wunan on 16-2-1.
 */
package com.etong.dc.user.service;

import com.etong.captcha.service.VerifyService;
import com.etong.dc.auth.AuthService;
import com.etong.dc.auth.TokenParam;
import com.etong.filter.AuthContext;
import com.etong.pt.data.service.UserData;
import com.etong.pt.data.service.UserInfo;
import com.etong.pt.data.user.PtUser;
import com.etong.pt.data.user.PtUserExample;
import com.etong.pt.provider.user.AuthorizationInfo;
import com.etong.pt.provider.user.LoginKey;
import com.etong.pt.provider.user.constants.Validator;
import com.etong.pt.utility.Md5Helper;
import com.etong.pt.utility.MessageHelper;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService {
    public static final String USER_EVENT = "USER_EVENT";
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserData userData;
    private VerifyService verifyService;
    private UserSession userSession;
    private AuthService authService;
    private MessageHelper msgHelper = new MessageHelper();

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public void setVerifyService(VerifyService verifyService) {
        this.verifyService = verifyService;
    }

    public void setUserSession(UserSession userSession) {
        this.userSession = userSession;
    }

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public PtResult register(UserInfo userInfo) {
        PtUser user = userInfo.getUser();

        if (user == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "没有设置用户信息", null);
        }

        if ((user.getF_phone() == null)
                && (user.getF_email() == null)
                && (user.getF_name() == null)) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "用户名、手机号或者邮箱至少设置一项", null);
        }

        if (user.getF_password() == null) {
            return new PtResult(PtCommonError.PT_ERROR_PARAMETER
                    , "没有设置用户密码", null);
        }

        msgHelper.sendMessageToServer(userInfo, USER_EVENT, "register");
        return userData.addUserInfo(userInfo);
    }

    @Override
    public PtResult register(UserInfo userInfo, String seq, String captcha) {
        String system = AuthContext.getInstance().getSystem();
        PtResult ptResult = verifyService.verifyCode(system, seq, captcha);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        return register(userInfo);
    }

    @Override
    public PtResult getLoginKey(String account) {
        PtResult ptResult = getUserByAccount(account);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<PtUser> userList = ptResult.getObject();
        PtUser user = userList.get(0);
        LoginKey loginKey = new LoginKey();
        loginKey.setSalt(user.getF_salt());
        loginKey.setVerify(user.getF_verify());
        loginKey.setExtraKey(String.format("%d", new Date().getTime()));
        String system = AuthContext.getInstance().getSystem();
        msgHelper.sendMessageToServer(user, USER_EVENT, "prelogin");
        return userSession.setLoginKey(account, system, loginKey);
    }

    @Override
    public PtResult loginByPwd(String account, String pwd) {
        PtResult ptResult = getUserByAccount(account);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        List<PtUser> userList = ptResult.getObject();
        PtUser user = userList.get(0);
        String system = AuthContext.getInstance().getSystem();
        ptResult = userSession.getLoginKey(account, system);

        if (!ptResult.isSucceed()) {
            return ptResult;
        }

        LoginKey loginKey = ptResult.getObject();
        String tmpPwd = Md5Helper.Str2MD5(Md5Helper.Str2MD5(user.getF_password()
                + loginKey.getSalt()) + loginKey.getExtraKey());

        if (tmpPwd.compareToIgnoreCase(pwd) != 0) {
            return new PtResult(PtCommonError.PT_ERROR_PASSWORD, "密码验证错误", null);
        }

        TokenParam tokenParam = new TokenParam();
        tokenParam.setUserId(user.getF_mid().longValue());
        tokenParam.setSystem(system);
        tokenParam.setIssuer("admin_" + system);
        tokenParam.setExpireTime(600);
        ptResult = authService.createToken(tokenParam);

        if (!ptResult.isSucceed()) {
            logger.error("Token生成错误，account: {}, system: {}, result: {}"
                    , account, system, ptResult.toString());
            return ptResult;
        }

        AuthorizationInfo authorizationInfo = new AuthorizationInfo();
        authorizationInfo.setToken((String) ptResult.getObject());
        authorizationInfo.setAppId(system);
        authorizationInfo.setUserId(user.getF_mid());
        authorizationInfo.setUserName(user.getF_name());
        authorizationInfo.setPhone(user.getF_phone());
        authorizationInfo.setEmail(user.getF_email());

        logger.info("登录成功，userId: {}, token: {}"
                , user.getF_mid(), authorizationInfo.getToken());
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, authorizationInfo);
    }

    @Override
    public PtResult logout(String account) {
        logger.info("用户注销，帐号：{}，系统：{}", account
                , AuthContext.getInstance().getSystem());
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }

    private PtResult getUserByAccount(String account) {
        PtUserExample userExample = new PtUserExample();

        if (Validator.isEmail(account)) {
            userExample.or().andF_emailEqualTo(account);
        } else if (Validator.isMobile(account)) {
            userExample.or().andF_phoneEqualTo(account);
        } else {
            userExample.or().andF_nameEqualTo(account);
        }

        return userData.getUser(userExample);
    }
}
