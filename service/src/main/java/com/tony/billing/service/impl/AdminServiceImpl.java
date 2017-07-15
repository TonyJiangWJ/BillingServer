package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumDeleted;
import com.tony.billing.dao.AdminDao;
import com.tony.billing.entity.Admin;
import com.tony.billing.service.AdminService;
import com.tony.billing.util.RedisUtils;
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

    private final Long VERIFY_TIME = 3600 * 24 * 1000L;
    @Override
    public Admin login(Admin admin) {
        Admin checkUser = adminDao.preLogin(admin);
        if (checkUser != null) {
            RedisUtils.del(checkUser.getTokenId());
            checkUser.setTokenId(TokenUtil.getToken(checkUser.getCode(), checkUser.getUserName(), checkUser.getPassword()));
            checkUser.setTokenVerify(VERIFY_TIME);
            checkUser.setLastLogin(new Date());
            if (adminDao.doLogin(checkUser) > 0) {
                RedisUtils.set(checkUser.getTokenId(), deleteSecret(checkUser), VERIFY_TIME/1000);
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
            } else {
                return -1L;
            }
        }
        return -2L;
    }

    @Override
    public boolean logout(String tokenId) {
        return RedisUtils.del(tokenId);
    }

    private Admin deleteSecret(Admin admin){
        Admin admin1 = new Admin();
        admin1.setId(admin.getId());
        admin1.setTokenId(admin.getTokenId());
        admin1.setUserName(admin.getUserName());
        admin1.setLastLogin(admin.getLastLogin());
        return admin1;
    }
}
