package com.tony.billing.dao.impl;

import com.tony.billing.dao.LiabilityParentTypeDao;
import com.tony.billing.dao.mapper.LiabilityParentTypeMapper;
import com.tony.billing.entity.LiabilityParentType;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 2018/04/24
 */
@Repository
public class LiabilityParentTypeDaoImpl implements LiabilityParentTypeDao {

    @Resource
    private LiabilityParentTypeMapper liabilityParentTypeMapper;

    @Override
    public Long insert(LiabilityParentType liabilityParentType) {
        liabilityParentType.setCreateTime(new Date());
        liabilityParentType.setModifyTime(new Date());
        return liabilityParentTypeMapper.insert(liabilityParentType);
    }

    @Override
    public Integer update(LiabilityParentType liabilityParentType) {
        liabilityParentType.setModifyTime(new Date());
        return liabilityParentTypeMapper.update(liabilityParentType);
    }

    @Override
    public List<LiabilityParentType> page(Map params) {
        return liabilityParentTypeMapper.page(params);
    }

    @Override
    public List<LiabilityParentType> list(LiabilityParentType liabilityParentType) {
        return liabilityParentTypeMapper.list(liabilityParentType);
    }

    @Override
    public LiabilityParentType getById(Long id) {
        return liabilityParentTypeMapper.getById(id);
    }

}
