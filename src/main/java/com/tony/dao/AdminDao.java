package com.tony.dao;

import com.tony.entity.Admin;
import org.springframework.stereotype.Repository;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@Repository
public interface AdminDao {
    Long doLogin(Admin admin);

    Admin preLogin(Admin admin);

    Admin loginCheck(String tokenId);

    Admin queryByUserName(String userName);

    Long register(Admin admin);

    Long logout(Long userId);
}
