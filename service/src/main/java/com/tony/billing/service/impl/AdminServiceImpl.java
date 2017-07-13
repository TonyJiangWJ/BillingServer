package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumDeleted;
import com.tony.billing.dao.AdminDao;
import com.tony.billing.entity.Admin;
import com.tony.billing.service.AdminService;
import com.tony.billing.util.TokenUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    @Override
    public Admin login(Admin admin) {
        Admin checkUser = adminDao.preLogin(admin);
        if (checkUser != null) {
            checkUser.setTokenId(TokenUtil.getToken(checkUser.getCode(), checkUser.getUserName(), checkUser.getPassword()));
            checkUser.setTokenVerify(3600 * 24 * 1000L);
            checkUser.setLastLogin(new Date());
            if (adminDao.doLogin(checkUser) > 0) {
                return checkUser;
            }
        }
        return null;
    }

    @Override
    public Admin checkToken(String tokenId) {
        Admin checkUser = adminDao.loginCheck(tokenId);
        if (checkUser != null) {
            if (checkUser.getLastLogin().getTime() + checkUser.getTokenVerify() > System.currentTimeMillis()) {
                return checkUser;
            }
        }
        return null;
    }

    @Override
    public Long register(Admin admin) {
        if (null == adminDao.queryByUserName(admin.getUserName())) {
            admin.setCreateTime(new Date());
            admin.setModifyTime(admin.getCreateTime());
            admin.setVersion(1);
            admin.setIsDeleted(EnumDeleted.NOT_DELETED.val());
            if (adminDao.register(admin) > 0) {
                return admin.getId();
            }else{
                return -1L;
            }
        }
        return -2L;
    }

    @Override
    public Long logout(Long userId) {
        return adminDao.logout(userId);
    }
}
