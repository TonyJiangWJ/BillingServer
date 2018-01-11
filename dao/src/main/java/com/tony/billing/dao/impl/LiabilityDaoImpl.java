package com.tony.billing.dao.impl;

import com.tony.billing.dao.LiabilityDao;
import com.tony.billing.dao.mapper.LiabilityMapper;
import com.tony.billing.entity.Liability;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class LiabilityDaoImpl implements LiabilityDao {

    @Resource
    private LiabilityMapper liabilityMapper;

    @Override
    public Long insert(Liability liability) {
        return liabilityMapper.insert(liability);
    }

    @Override
    public Long update(Liability liability) {
        return liabilityMapper.update(liability);
    }

    @Override
    public List<Liability> page(Map params) {
        return liabilityMapper.page(params);
    }

    @Override
    public List<Liability> list(Liability liability) {
        return liabilityMapper.list(liability);
    }
}
