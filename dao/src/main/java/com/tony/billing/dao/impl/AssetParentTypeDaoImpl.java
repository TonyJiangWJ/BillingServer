package com.tony.billing.dao.impl;

import com.tony.billing.dao.AssetParentTypeDao;
import com.tony.billing.dao.mapper.AssetParentTypeMapper;
import com.tony.billing.entity.AssetParentType;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 2018/04/24
 */
@Repository
public class AssetParentTypeDaoImpl implements AssetParentTypeDao {

    @Resource
    private AssetParentTypeMapper assetParentTypeMapper;

    @Override
    public Long insert(AssetParentType assetParentType) {
        assetParentType.setCreateTime(new Date());
        assetParentType.setModifyTime(new Date());
        return assetParentTypeMapper.insert(assetParentType);
    }

    @Override
    public Integer update(AssetParentType assetParentType) {
        assetParentType.setModifyTime(new Date());
        return assetParentTypeMapper.update(assetParentType);
    }

    @Override
    public List<AssetParentType> page(Map params) {
        return assetParentTypeMapper.page(params);
    }

    @Override
    public List<AssetParentType> list(AssetParentType assetParentType) {
        return assetParentTypeMapper.list(assetParentType);
    }

    @Override
    public AssetParentType getById(Long id) {
        return assetParentTypeMapper.getById(id);
    }

}
