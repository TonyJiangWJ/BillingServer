package com.tony.billing.service;

import com.tony.billing.entity.Admin;

/**
 * Author by TonyJiang on 2017/5/18.
 */
public interface AdminService {

    Admin login(Admin admin);

    Long register(Admin admin);

    boolean logout(String tokenId);
}
