package com.tony.billing.service;

import com.tony.billing.entity.LoginLog;

import java.util.List;

/**
 * @author: jiangwj20966 on 2018/1/25
 */
public interface LoginLogService {

    Long addLog(LoginLog loginLog);

    List<LoginLog> selectList();
}
