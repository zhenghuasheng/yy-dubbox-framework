package com.etong.sms.service.impl;

import org.apache.http.HttpStatus;

import javax.ws.rs.Produces;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/18.
 */
@Produces
public class ControlFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) throws IOException {

        if (containerRequestContext.getMethod().equals("OPTIONS")) {
            containerResponseContext.getHeaders().add("access-control-allow-headers", "Content-Type, Content-Range, Content-Disposition, X-Requested-With");
            containerResponseContext.getHeaders().add("access-control-allow-methods", "OPTIONS, HEAD, GET, POST, PUT, DELETE");
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            try {
                containerResponseContext.setStatus(HttpStatus.SC_OK);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers"
                    , "Content-Type, Content-Range, Content-Disposition, X-Requested-With");
        }
    }
}
