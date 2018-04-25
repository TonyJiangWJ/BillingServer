package com.tony.billing.dao;

import com.tony.billing.entity.LiabilityParentType;

import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 2018/04/24
 */
public interface LiabilityParentTypeDao {

    Long insert(LiabilityParentType liabilityParentType);

    LiabilityParentType getById(Long id);

    Integer update(LiabilityParentType log);

    List<LiabilityParentType> list(LiabilityParentType condition);

    List<LiabilityParentType> page(Map params);
}
