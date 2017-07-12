package com.tony.billing.interceptors;

import com.alibaba.fastjson.JSON;
import com.tony.billing.entity.Admin;
import com.tony.billing.request.wapper.TokenServletRequest;
import com.tony.billing.service.AdminService;
import com.tony.billing.util.AuthUtil;
import com.tony.billing.util.CookieUtil;
import com.tony.billing.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author by TonyJiang on 2017/7/1.
 */
@Component
public class InterceptorDemo implements HandlerInterceptor {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(InterceptorDemo.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("收到请求{}", JSON.toJSONString(httpServletRequest.getParameterMap()));
        String url = httpServletRequest.getRequestURL().toString();
        logger.info("headerInfo:{}", httpServletRequest.getHeader("Host"));
        httpServletResponse.setHeader("Access-Control-Allow-Origin", url.substring(0, 7 + url.substring(7).indexOf("/")));
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        if (!isUserLogin(httpServletRequest)) {
            logger.info("user not login:{}", JSON.toJSONString(CookieUtil.getCookie("token", httpServletRequest)));
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(ResponseUtil.loginError()));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("请求处理结束：Access-Control-Allow-Origin:{}", httpServletResponse.getHeader("Access-Control-Allow-Origin"));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("完全结束");
    }

    private boolean isUserLogin(HttpServletRequest request) throws Exception {
        Cookie tokenCok = CookieUtil.getCookie("token", request);
        if (tokenCok != null) {
            String tokenId = AuthUtil.getUserTokenId(tokenCok.getValue());
            Admin admin = adminService.checkToken(tokenId); // TODO should replace by no-sql database
            if (admin != null) {
                if (request instanceof TokenServletRequest) {
                    ((TokenServletRequest) request).addParameter("tokenId", tokenId);
                    ((TokenServletRequest) request).addParameter("userId", String.valueOf(admin.getId()));
                } else if(request instanceof StandardMultipartHttpServletRequest){
                    ((TokenServletRequest) ((StandardMultipartHttpServletRequest) request).getRequest()).addParameter("tokenId", tokenId);
                    ((TokenServletRequest) ((StandardMultipartHttpServletRequest) request).getRequest()).addParameter("userId", String.valueOf(admin.getId()));
                }
                return true;
            }
        }
        return false;
    }


    private void demo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                logger.info("cookie[name:{} value:{}]", cookie.getName(), cookie.getValue());
            }
            String url = httpServletRequest.getRequestURL().toString();
            logger.info("headerInfo:{}", httpServletRequest.getHeader("Host"));
            httpServletResponse.setHeader("Access-Control-Allow-Origin", url.substring(0, 7 + url.substring(7).indexOf("/")));
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        } else {
            httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        }
        Cookie cookie = new Cookie("preDemoCookieName", "preDemoCookieValue");
        cookie.setPath("/");
        cookie.setMaxAge(60880);
        httpServletResponse.addCookie(cookie);
    }
}
