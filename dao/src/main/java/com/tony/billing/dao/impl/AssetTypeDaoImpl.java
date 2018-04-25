package com.tony.billing.dao.impl;

import com.tony.billing.entity.AssetType;
import com.tony.billing.dao.AssetTypeDao;
import com.tony.billing.dao.mapper.AssetTypeMapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
/**
* @author jiangwj20966 2018/04/24
*/
@Repository
public class AssetTypeDaoImpl implements AssetTypeDao {

    @Resource
    private AssetTypeMapper assetTypeMapper;

    @Override
    public Long insert(AssetType assetType) {
        assetType.setCreateTime(new Date());
        assetType.setModifyTime(new Date());
        return assetTypeMapper.insert(assetType);
    }

    @Override
    public Integer update(AssetType assetType) {
        assetType.setModifyTime(new Date());
        return assetTypeMapper.update(assetType);
    }

    @Override
    public List<AssetType> page(Map params) {
        return assetTypeMapper.page(params);
    }

    @Override
    public List<AssetType> list(AssetType assetType) {
        return assetTypeMapper.list(assetType);
    }

    @Override
    public AssetType getById(Long id) {
        return assetTypeMapper.getById(id);
    }

}
