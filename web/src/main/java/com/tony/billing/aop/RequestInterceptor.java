package com.tony.billing.aop;

import com.alibaba.fastjson.JSON;
import com.tony.billing.request.BaseRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Author by TonyJiang on 2017/7/2.
 */
//@Aspect
//@Component
@Deprecated
public class RequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Before("execution(* com.tony.billing.controller.*Controller.*(..))")
    public void changeParam(JoinPoint point) {
        Object[] params = point.getArgs();
        if (params != null) {
            String tokenId = null;
            BaseRequest request = null;
            for (Object param : params) {
                if (param instanceof BaseRequest) {
                    request = (BaseRequest) param;
                    logger.info("requestInfo:{}", JSON.toJSONString(request));
                }
                if (param instanceof HttpServletRequest) {
                    tokenId = (String) ((HttpServletRequest) param).getAttribute("tokenId");
                }
            }
            if (StringUtils.isNotEmpty(tokenId) && request != null) {
                request.setTokenId(tokenId);
                logger.info("修改参数成功");
            }
        }
    }
}
