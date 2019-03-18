package com.tony.billing.service.impl;

import com.tony.billing.dao.mapper.AdminMapper;
import com.tony.billing.entity.Admin;
import com.tony.billing.entity.ModifyAdmin;
import com.tony.billing.service.AdminService;
import com.tony.billing.test.BaseServiceTest;
import com.tony.billing.util.RSAUtil;
import com.tony.billing.util.RedisUtils;
import io.jsonwebtoken.lang.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @author jiangwenjie 2019-02-02
 */
public class AdminServiceImplTest extends BaseServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private RSAUtil rsaUtil;
    @Autowired
    private RedisUtils redisUtils;

    private String userName;

    private String password;

    private String tokenId;

    private String newPassword;

    @Before
    public void setUp() {
        userName = "justtest";
        password = rsaUtil.encryptWithPubKey("123456");
        newPassword = rsaUtil.encryptWithPubKey("newPassword");
    }


    @Test
    public void login() {
        Admin loginUser = new Admin();
        loginUser.setUserName(userName);
        loginUser.setPassword(password);
        loginUser = adminService.login(loginUser);

        Assert.notNull(loginUser);
        Assert.notNull(loginUser.getTokenId());
        tokenId = loginUser.getTokenId();
        Optional<Admin> store = redisUtils.get(tokenId, Admin.class);
        Assert.isTrue(store.isPresent());
    }

    @Test
    public void register() {
        Admin register = new Admin();
        register.setUserName(userName);
        register.setPassword(password);
        Long result = adminService.register(register);
        Assert.isTrue(-2L == result);
        register.setUserName("testRegister");
        result = adminService.register(register);
        Assert.isTrue(result > 0);
        debugInfo("new register userId:{}", result);
    }

    @Test
    public void logout() {
        this.login();
        adminService.logout(tokenId);
        Optional store = redisUtils.get(tokenId, Admin.class);
        Assert.isTrue(!store.isPresent());
    }

    @Test
    public void modifyPwd() {
        Admin admin = adminMapper.queryByUserName(userName);
        Assert.notNull(admin);
        ModifyAdmin modifyAdmin = new ModifyAdmin();
        modifyAdmin.setId(admin.getId());
        modifyAdmin.setUserName(userName);
        modifyAdmin.setPassword(password);
        modifyAdmin.setNewPassword(newPassword);
        Assert.isTrue(adminService.modifyPwd(modifyAdmin));
    }

    @Test
    public void preResetPwd() {
        Admin admin = adminService.preResetPwd(userName);
        Assert.notNull(admin);
        Assert.notNull(admin.getTokenId());
        debugInfo("token is: {}", admin.getTokenId());
        tokenId = admin.getTokenId();
    }

    @Test
    public void resetPwd() {
        this.preResetPwd();
        ModifyAdmin admin = new ModifyAdmin();
        admin.setTokenId(tokenId);
        admin.setNewPassword(newPassword);
        Assert.isTrue(adminService.resetPwd(admin));
        Admin loginAdmin = new Admin();
        loginAdmin.setUserName(userName);
        loginAdmin.setPassword(newPassword);
        Assert.notNull(adminService.login(loginAdmin));

    }
}