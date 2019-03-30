package com.tony.billing.entity;

import com.tony.billing.annotation.Table;
import com.tony.billing.entity.base.BaseEntity;

import java.util.Date;

/**
 * @author: jiangwj20966 on 2018/1/25
 */
@Table("t_login_log")
public class LoginLog extends BaseEntity {
    private String userName;
    private String loginResult;
    private String loginIp;
    private String code;
    private String msg;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginResult() {
        return loginResult;
    }

    public void setLoginResult(String loginResult) {
        this.loginResult = loginResult;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
