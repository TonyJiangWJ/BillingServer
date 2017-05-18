package com.tony.service.impl;

import com.tony.dao.AdminDao;
import com.tony.entity.Admin;
import com.tony.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminDao adminDao;

    public List<Admin> listAdmin() {
        return adminDao.listAdmin();
    }
}
