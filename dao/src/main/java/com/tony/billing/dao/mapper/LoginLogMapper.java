package com.tony.billing.dao.mapper;

import com.tony.billing.entity.LoginLog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: jiangwj20966 on 2018/1/25
 */
@Repository
public interface LoginLogMapper {

    public Long insert(LoginLog log);

    public List<LoginLog> selectList();
}
