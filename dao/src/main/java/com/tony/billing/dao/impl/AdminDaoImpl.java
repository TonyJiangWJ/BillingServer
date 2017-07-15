package com.tony.billing.dao.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.dao.AdminDao;
import com.tony.billing.dao.mapper.AdminMapper;
import com.tony.billing.entity.Admin;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author by TonyJiang on 2017/7/12.
 */
@Service("adminDao")
public class AdminDaoImpl implements AdminDao {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Long doLogin(Admin admin) {
        Preconditions.checkNotNull(admin.getUserName(), "userName should not be null");
        Preconditions.checkNotNull(admin.getPassword(), "password should not be null");
        return adminMapper.doLogin(admin);
    }

    @Override
    public Admin preLogin(Admin admin) {
        Preconditions.checkNotNull(admin.getUserName(), "userName should not be null");
        Preconditions.checkNotNull(admin.getPassword(), "password should not be null");
        return adminMapper.preLogin(admin);
    }

    @Override
    public Admin loginCheck(String tokenId) {
        Preconditions.checkNotNull(tokenId, "tokenId must not be null");
        return adminMapper.loginCheck(tokenId);
    }

    @Override
    public Admin queryByUserName(String userName) {
        return adminMapper.queryByUserName(userName);
    }

    @Override
    public Long register(Admin admin) {
        Preconditions.checkNotNull(admin.getUserName(), "userName should not be null");
        Preconditions.checkNotNull(admin.getPassword(), "password should not be null");
        return adminMapper.register(admin);
    }

    @Override
    public Long logout(Long userId) {
        return adminMapper.logout(userId);
    }
}
