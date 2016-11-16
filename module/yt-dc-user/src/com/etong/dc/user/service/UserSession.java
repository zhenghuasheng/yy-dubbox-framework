/**
 * Created by wunan on 16-2-3.
 */
package com.etong.dc.user.service;

import com.etong.pt.provider.user.LoginKey;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import com.google.code.ssm.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSession {
    public static final String MEM_SESSION = "MEM:SESSION";
    private static Logger logger = LoggerFactory.getLogger(UserSession.class);

    @UpdateSingleCache(namespace = MEM_SESSION, expiration = 300)
    @ReturnDataUpdateContent
    PtResult setLoginKey(@ParameterValueKeyProvider(order = 0) String account
            , @ParameterValueKeyProvider(order = 1) String system
            , LoginKey loginKey) {
        logger.debug("设置用户登录KEY，帐号：{}，系统：{}，KEY：{}"
                , account, system, loginKey);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, loginKey);
    }

    @ReadThroughSingleCache(namespace = MEM_SESSION, expiration = 300)
    PtResult getLoginKey(@ParameterValueKeyProvider(order = 0) String account
            , @ParameterValueKeyProvider(order = 1) String system) {
        logger.info("用户登录KEY不存在或者已经过期，帐号：{}，系统：{}", account, system);
        return new PtResult(PtCommonError.PT_ERROR_NODATA, "没有找到用户登录会话", null);
    }

    @InvalidateSingleCache(namespace = MEM_SESSION)
    PtResult delLoginKey(String account, String system) {
        logger.debug("移除用户登录KEY，帐号：{}，系统：{}", account, system);
        return new PtResult(PtCommonError.PT_ERROR_SUCCESS, null, null);
    }
}
