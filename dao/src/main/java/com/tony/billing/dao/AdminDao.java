package com.tony.billing.dao;

import com.tony.billing.entity.Admin;

/**
 * @author by TonyJiang on 2017/5/18.
 */
public interface AdminDao {
    Long doLogin(Admin admin);

    Admin preLogin(Admin admin);

    Admin loginCheck(String tokenId);

    Admin queryByUserName(String userName);

    Long register(Admin admin);

    Long logout(Long userId);

    Long modifyPwd(Admin admin);

    Admin getAdminById(Long userId);
}
