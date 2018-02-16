package com.tony.billing.service.impl;

import com.tony.billing.dao.LoginLogDao;
import com.tony.billing.entity.LoginLog;
import com.tony.billing.service.LoginLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: jiangwj20966 on 2018/1/25
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Resource
    private LoginLogDao loginLogDao;

    @Override
    public Long addLog(LoginLog loginLog) {
        return loginLogDao.insert(loginLog);
    }

    @Override
    public List<LoginLog> selectList() {
        return loginLogDao.selectList();
    }
}
