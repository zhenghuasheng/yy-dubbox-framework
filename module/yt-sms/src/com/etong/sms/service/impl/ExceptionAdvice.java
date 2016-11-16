package com.etong.sms.service.impl;

import com.etong.pt.utility.PtCommonError;
import com.etong.pt.utility.PtResult;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by Administrator on 2015/12/1.
 */
@Order(2)
@Aspect
@Component
public class ExceptionAdvice {

    @Around(value = "execution(public com.etong.pt.utility.PtResult *.*(..)) ")
    public Object aroundMethod(ProceedingJoinPoint pjd) {
        Object result = null;
        String methodName = pjd.getSignature().getName();
        //执行目标方法
        try {
            //前置通知
            System.out.println("The method " + methodName + " begins with " + Arrays.asList(pjd.getArgs()));
            result = pjd.proceed();
            //返回通知
            System.out.println("The method " + methodName + " ends with " + Arrays.asList(pjd.getArgs()));
        } catch (Throwable e) {
            //异常通知
            System.out.println("The method " + methodName + " occurs expection : " + e);
            return new PtResult(PtCommonError.PT_ERROR_DB, e.getMessage(), null);
        }
        //后置通知
        System.out.println("The method " + methodName + " ends");
        return result;
    }
}
