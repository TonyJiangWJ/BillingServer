package com.tony.billing.entity;

import com.tony.billing.entity.base.BaseVersionedEntity;

import java.util.Date;

/**
 * @author by TonyJiang on 2017/5/18.
 */
public class Admin extends BaseVersionedEntity {
    private String tokenId;
    private Long tokenVerify;
    private String code;
    private String userName;
    private String email;
    private String password;
    private Date lastLogin;

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Long getTokenVerify() {
        return tokenVerify;
    }

    public void setTokenVerify(Long tokenVerify) {
        this.tokenVerify = tokenVerify;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

}
