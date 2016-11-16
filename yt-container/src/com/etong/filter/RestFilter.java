/**
 * Created by wunan on 15-11-19.
 */
package com.etong.filter;


import org.apache.http.HttpStatus;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class RestFilter implements ContainerResponseFilter {

    public static final String ALLOW_HEADERS = "Content-Type, Content-Range, Content-Disposition, X-Requested-With, auth-token";

    @Override
    public void filter(ContainerRequestContext containerRequestContext
            , ContainerResponseContext containerResponseContext) throws IOException {
        if (HttpMethod.OPTIONS.equalsIgnoreCase(containerRequestContext.getMethod())) {
            containerResponseContext.getHeaders().add("access-control-allow-headers"
                    , ALLOW_HEADERS);
            containerResponseContext.getHeaders().add("access-control-allow-methods"
                    , "OPTIONS, HEAD, GET, POST, PUT, DELETE");
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            containerResponseContext.setStatus(HttpStatus.SC_OK);
        } else {
            containerResponseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
            containerResponseContext.getHeaders().add("Access-Control-Allow-Headers"
                    , ALLOW_HEADERS);
        }
    }
}
