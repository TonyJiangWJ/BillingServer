package com.tony.dao;

import com.tony.entity.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@Repository
public interface AdminDao {
    List<Admin> listAdmin();
}
