package com.tony.billing.interceptors;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author by TonyJiang on 2017/7/2.
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("收到请求{}", JSON.toJSONString(httpServletRequest.getParameterMap()));
        String url = httpServletRequest.getRequestURL().toString();
        logger.info("headerInfo:{}", httpServletRequest.getHeader("Host"));
        httpServletResponse.setHeader("Access-Control-Allow-Origin", url.substring(0, 7 + url.substring(7).indexOf("/")));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        return true;
    }

}
