/**
 * Created by wunan on 15-11-5.
 */
package com.etong.filter;

import com.alibaba.dubbo.rpc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerAuthFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(ProviderAuthFilter.class);

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String token = AuthContext.getInstance().getToken();
        RpcContext.getContext().setAttachment("auth-token", token);
        logger.debug("Consumer Filter Invoke {}, token {}", invocation.getMethodName(), token);
        return invoker.invoke(invocation);
    }
}
