package com.etong.filter;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSONException;
import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Administrator on 2015/12/2.
 */
@Aspect
public class MethodAnalysis {
    private static Logger logger = LoggerFactory.getLogger(MethodAnalysis.class);

    @Around(value = "execution(public com.etong.pt.utility.PtResult *.*(..)) ")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        //执行目标方法
        try {
            long begin = System.currentTimeMillis();
            //前置通知
            logger.debug("进入函数: {}, 参数: {}", methodName, Arrays.asList(pjd.getArgs()));

            result = pjd.proceed();
            //返回通知
            logger.debug("离开函数: {}, 用时: {} ms, 参数: {}", methodName
                    , System.currentTimeMillis() - begin, Arrays.asList(pjd.getArgs()));
        } catch (Throwable e) {
            //异常通知
            logger.error("函数异常: {}, 参数: {}, 异常信息: {}"
                    , methodName, Arrays.asList(pjd.getArgs()), e.getMessage());

            if (e instanceof SQLException) {
                return new PtResult(PtCommonError.PT_ERROR_DB, "数据库异常", null);
            } else if (e instanceof RpcException) {
                return new PtResult(PtCommonError.PT_ERROR_RPC, "远程调用异常", null);
            } else if (e instanceof JSONException) {
                return new PtResult(PtCommonError.PT_ERROR_JSON_PARSE, "JSON解析异常", null);
            } else {
                return new PtResult(PtCommonError.PT_ERROR_UNKOWN, "未知异常", null);
            }
        }

        return result;
    }
}
