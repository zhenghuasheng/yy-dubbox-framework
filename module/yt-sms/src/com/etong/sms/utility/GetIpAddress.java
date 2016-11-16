package com.etong.sms.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.StringUtils;


public class GetIpAddress {
    public static String getIpAddr(HttpServletRequest request) {
        if (null == request) {
            return null;
        }
        String proxs[] = {"X-Forwarded-For", "Proxy-ksClient-IP",
                "WL-Proxy-ksClient-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

        String ip = null;

        for (String prox : proxs) {
            ip = request.getHeader(prox);
            if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                continue;
            } else {
                break;
            }
        }
        if (StringUtils.isEmpty(ip)) {
            return request.getRemoteAddr();
        }

        return ip;
    }
}
