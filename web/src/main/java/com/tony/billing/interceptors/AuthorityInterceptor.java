package com.tony.billing.interceptors;

import com.alibaba.fastjson.JSON;
import com.tony.billing.entity.Admin;
import com.tony.billing.filters.wapper.TokenServletRequestWrapper;
import com.tony.billing.util.AuthUtil;
import com.tony.billing.util.CookieUtil;
import com.tony.billing.util.RedisUtils;
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
import java.util.Map;

/**
 * Author by TonyJiang on 2017/7/1.
 * 权限校验
 */
@Component
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthUtil authUtil;

    private Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

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
        logger.debug("请求处理结束：Access-Control-Allow-Origin:{}", httpServletResponse.getHeader("Access-Control-Allow-Origin"));
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.debug("完全结束");
    }

    private boolean isUserLogin(HttpServletRequest request) throws Exception {
        Cookie tokenCok = CookieUtil.getCookie("token", request);
        if (tokenCok != null) {
            String tokenId = authUtil.getUserTokenId(tokenCok.getValue());
            Map store = RedisUtils.get(tokenId, Admin.class);
            if (store != null) {
                Admin admin = (Admin) store.get(tokenId);
                if (request instanceof TokenServletRequestWrapper) {
                    ((TokenServletRequestWrapper) request).addParameter("tokenId", tokenId);
                    ((TokenServletRequestWrapper) request).addParameter("userId", String.valueOf(admin.getId()));
                } else if (request instanceof StandardMultipartHttpServletRequest) {
                    ((TokenServletRequestWrapper) ((StandardMultipartHttpServletRequest) request).getRequest()).addParameter("tokenId", tokenId);
                    ((TokenServletRequestWrapper) ((StandardMultipartHttpServletRequest) request).getRequest()).addParameter("userId", String.valueOf(admin.getId()));
                }
                return true;
            }
        }
        return false;
    }
}