package com.tony.billing.service.impl;

import com.tony.billing.dao.mapper.LoginLogMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.entity.LoginLog;
import com.tony.billing.service.LoginLogService;
import com.tony.billing.service.base.AbstractService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: jiangwj20966 on 2018/1/25
 */
@Service
public class LoginLogServiceImpl extends AbstractService<LoginLog> implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    protected AbstractMapper<LoginLog> getMapper() {
        return loginLogMapper;
    }

    @Override
    public Long addLog(LoginLog loginLog) {
        return super.insert(loginLog);
    }
}
