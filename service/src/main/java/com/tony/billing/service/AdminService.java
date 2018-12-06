package com.tony.billing.service;

import com.tony.billing.entity.Admin;
import com.tony.billing.entity.ModifyAdmin;

/**
 * @author by TonyJiang on 2017/5/18.
 */
public interface AdminService {

    Admin login(Admin admin);

    Long register(Admin admin);

    boolean logout(String tokenId);

    boolean modifyPwd(ModifyAdmin admin);
}
