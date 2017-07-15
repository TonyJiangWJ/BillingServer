package com.tony.billing.request.admin;

import com.tony.billing.request.BaseRequest;

/**
 * Author by TonyJiang on 2017/7/2.
 */
public class AdminLoginRequest extends BaseRequest {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
