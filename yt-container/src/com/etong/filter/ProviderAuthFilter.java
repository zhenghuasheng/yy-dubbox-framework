/**
 * Created by wunan on 15-11-4.
 */
package com.etong.filter;

import com.alibaba.dubbo.rpc.*;
import com.etong.data.auth.view.UserResource;
import com.etong.dc.auth.AuthService;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class ProviderAuthFilter implements Filter {
    public static final String AUTH_TOKEN = "auth-token";
    private static Logger logger = LoggerFactory.getLogger(ProviderAuthFilter.class);
    private AuthService authService;

    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext context = RpcContext.getContext();
        String token = context.getAttachment(AUTH_TOKEN);

        if (token == null) {
            HttpServletRequest request = context.getRequest(HttpServletRequest.class);

            if (request != null) {
                token = request.getHeader(AUTH_TOKEN);
            }
        }

        logger.debug("Provider Filter Invoke {}, token {}"
                , invocation.getMethodName(), token);

        if (token == null) {
            return new RpcResult(new PtResult(PtCommonError.PT_ERROR_VERIFY_TOKEN
                    , "访问接口令牌为空", null));
        }

        AuthContext.getInstance().setToken(token);
        PtResult ptResult = authService.checkAuth(token
                , invoker.getInterface().getName() + "." + invocation.getMethodName());

        if (!ptResult.isSucceed()) {
            if (ptResult.getPtError() == PtCommonError.PT_ERROR_NODATA) {
                return new RpcResult(new PtResult(PtCommonError.PT_ERROR_NOAUTH
                        , "访问权限验证失败，可能没有配置权限", null));
            } else {
                return new RpcResult(ptResult);
            }
        }

        UserResource userResource = ptResult.getObject();

        if ((userResource.getAvailable() == 0)) {
            return new RpcResult(new PtResult(PtCommonError.PT_ERROR_NOAUTH, "无访问接口的权限", null));
        }

        AuthContext.getInstance().setUserId(userResource.getMid());
        AuthContext.getInstance().setSystem(userResource.getStid());
        Result result = invoker.invoke(invocation);
        AuthContext.getInstance().reset();
        return result;
    }
}
