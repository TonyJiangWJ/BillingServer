package com.tony.interceptors;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author by TonyJiang on 2017/7/2.
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
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

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
