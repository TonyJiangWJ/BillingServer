package com.tony.service;

import com.tony.entity.Admin;

/**
 * Author by TonyJiang on 2017/5/18.
 */
public interface AdminService {

    Admin login(Admin admin);

    Admin checkToken(String tokenId);

    Long register(Admin admin);

    Long logout(Long userId);
}
