package com.tony.billing.dao.impl;

import com.tony.billing.dao.LoginLogDao;
import com.tony.billing.dao.mapper.LoginLogMapper;
import com.tony.billing.entity.LoginLog;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Author: jiangwj20966 on 2018/1/25
 */
@Repository
public class LoginLogDaoImpl implements LoginLogDao {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Override
    public Long insert(LoginLog log) {
        log.setCreateTime(new Date());
        log.setModifyTime(new Date());
        if (loginLogMapper.insert(log) > 0) {
            return log.getId();
        } else {
            return -1L;
        }
    }

    @Override
    public List<LoginLog> selectList() {
        return loginLogMapper.selectList();
    }
}
