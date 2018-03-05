package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumDeleted;
import com.tony.billing.dao.AdminDao;
import com.tony.billing.entity.Admin;
import com.tony.billing.entity.ModifyAdmin;
import com.tony.billing.service.AdminService;
import com.tony.billing.util.RSAUtil;
import com.tony.billing.util.RedisUtils;
import com.tony.billing.util.ShaSignHelper;
import com.tony.billing.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@Service
public class AdminServiceImpl implements AdminService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private AdminDao adminDao;

    private final Long VERIFY_TIME = 3600 * 24 * 1000L;

    @Resource
    private RSAUtil rsaUtil;

    @Value("${pwd.salt:springboot}")
    private String pwdSalt;

    @Override
    public Admin login(Admin admin) {
        admin.setPassword(sha256(rsaUtil.decrypt(admin.getPassword())));
        if (admin.getPassword() == null) {
            logger.error("password error");
            return null;
        }
        logger.debug("salt:{}", pwdSalt);
        Admin checkUser = adminDao.preLogin(admin);
        if (checkUser != null) {
            RedisUtils.del(checkUser.getTokenId());
            checkUser.setTokenId(TokenUtil.getToken(checkUser.getCode(), checkUser.getUserName(), checkUser.getPassword()));
            checkUser.setTokenVerify(VERIFY_TIME);
            checkUser.setLastLogin(new Date());
            if (adminDao.doLogin(checkUser) > 0) {
                RedisUtils.set(checkUser.getTokenId(), deleteSecret(checkUser), VERIFY_TIME / 1000);
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
            admin.setPassword(sha256(rsaUtil.decrypt(admin.getPassword())));
            if (admin.getPassword() == null) {
                logger.error("password error");
                return -1L;
            }
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

    @Override
    public boolean modifyPwd(ModifyAdmin admin) {
        admin.setNewPassword(sha256(admin.getNewPassword()));
        admin.setPassword(sha256(admin.getPassword()));
        if (admin.getNewPassword() == null) {
            return false;
        }
        Admin stored = adminDao.getAdminById(admin.getId());
        if (stored != null && StringUtils.equals(stored.getPassword(), admin.getPassword())) {
            stored.setPassword(admin.getNewPassword());
            return adminDao.modifyPwd(stored) > 0;
        }
        return false;
    }

    private Admin deleteSecret(Admin admin) {
        Admin admin1 = new Admin();
        admin1.setId(admin.getId());
        admin1.setTokenId(admin.getTokenId());
        admin1.setUserName(admin.getUserName());
        admin1.setLastLogin(admin.getLastLogin());
        return admin1;
    }

    private String sha256(String pwd) {
        if (pwd != null) {
            return ShaSignHelper.sign(pwd, pwdSalt);
        } else {
            return null;
        }
    }
}
