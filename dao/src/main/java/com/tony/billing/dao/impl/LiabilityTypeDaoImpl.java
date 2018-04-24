package com.tony.billing.dao.impl;

import com.tony.billing.dao.LiabilityTypeDao;
import com.tony.billing.dao.mapper.LiabilityTypeMapper;
import com.tony.billing.entity.LiabilityType;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 2018/04/24
 */
@Repository
public class LiabilityTypeDaoImpl implements LiabilityTypeDao {

    @Resource
    private LiabilityTypeMapper liabilityTypeMapper;

    @Override
    public Long insert(LiabilityType liabilityType) {
        liabilityType.setCreateTime(new Date());
        liabilityType.setModifyTime(new Date());
        return liabilityTypeMapper.insert(liabilityType);
    }

    @Override
    public Integer update(LiabilityType liabilityType) {
        liabilityType.setModifyTime(new Date());
        return liabilityTypeMapper.update(liabilityType);
    }

    @Override
    public List<LiabilityType> page(Map params) {
        return liabilityTypeMapper.page(params);
    }

    @Override
    public List<LiabilityType> list(LiabilityType liabilityType) {
        return liabilityTypeMapper.list(liabilityType);
    }

    @Override
    public LiabilityType getById(Long id) {
        return liabilityTypeMapper.getById(id);
    }

}
