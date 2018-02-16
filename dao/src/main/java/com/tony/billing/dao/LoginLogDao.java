package com.tony.billing.dao;

import com.tony.billing.entity.LoginLog;

import java.util.List;

/**
 * Author: jiangwj20966 on 2018/1/25
 */
public interface LoginLogDao {
    public Long insert(LoginLog log);

    public List<LoginLog> selectList();
}
