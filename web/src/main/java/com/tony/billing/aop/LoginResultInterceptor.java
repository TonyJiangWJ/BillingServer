package com.tony.billing.aop;

import com.alibaba.fastjson.JSON;
import com.tony.billing.entity.LoginLog;
import com.tony.billing.request.admin.AdminLoginRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.service.LoginLogService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
public class LoginResultInterceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private LoginLogService loginLogService;

    @AfterReturning(returning = "returnValue", pointcut = "execution(* com.tony.billing.controller.AdminController.login(..))")
    public void getResult(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        AdminLoginRequest loginRequest = (AdminLoginRequest) args[0];
        HttpServletRequest httpServletRequest = (HttpServletRequest) args[1];
        BaseResponse response = (BaseResponse) returnValue;

        LoginLog loginLog = new LoginLog();
        loginLog.setLoginIp(httpServletRequest.getHeader("X-Real-IP"));
        loginLog.setUserName(loginRequest.getUserName());
        loginLog.setLoginResult(JSON.toJSONString(response));
        loginLog.setCode(response.getCode());
        loginLog.setMsg(response.getMsg());
        loginLogService.addLog(loginLog);
        logger.info("{} 请求登录, 结果：{}", loginLog.getUserName(), loginLog.getLoginResult());
        logger.info("ip地址：{}", loginLog.getLoginIp());
        Long id;
        if ((id = loginLog.getId()) != null) {
            logger.info("日志保存成功，id:{}", id);
        } else {
            logger.error("日志保存失败");
        }
    }

    @AfterReturning(returning = "returnValue", pointcut = "execution(* com.tony.billing.controller.thymeleaf.LoginController.doLogin(..))")
    public void getThymeleafResult(JoinPoint point, Object returnValue) {
        Object[] args = point.getArgs();
        AdminLoginRequest loginRequest = (AdminLoginRequest) args[1];
        HttpServletRequest httpServletRequest = (HttpServletRequest) args[2];
        String responseView = (String) returnValue;

        LoginLog loginLog = new LoginLog();
        loginLog.setLoginIp(httpServletRequest.getHeader("X-Real-IP"));
        loginLog.setUserName(loginRequest.getUserName());
        loginLog.setLoginResult(responseView);
        BaseResponse response = null;
        if (StringUtils.equals(responseView, "/thymeleaf/login/success")) {
            response = ResponseUtil.success();
        } else {
            response = ResponseUtil.error();
        }
        loginLog.setCode(response.getCode());
        loginLog.setMsg(response.getMsg());
        loginLogService.addLog(loginLog);
        logger.info("{} 请求登录, 结果：{}", loginLog.getUserName(), loginLog.getLoginResult());
        logger.info("ip地址：{}", loginLog.getLoginIp());
        Long id;
        if ((id = loginLog.getId()) != null) {
            logger.info("日志保存成功，id:{}", id);
        } else {
            logger.error("日志保存失败");
        }
    }
}
