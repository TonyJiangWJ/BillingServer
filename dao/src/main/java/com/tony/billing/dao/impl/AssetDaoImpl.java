package com.tony.billing.dao.impl;

import com.tony.billing.dao.AssetDao;
import com.tony.billing.dao.mapper.AssetMapper;
import com.tony.billing.entity.Asset;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class AssetDaoImpl implements AssetDao {

    @Resource
    private AssetMapper assetMapper;

    @Override
    public Long insert(Asset asset) {
        asset.setCreateTime(new Date());
        asset.setModifyTime(new Date());
        return assetMapper.insert(asset);
    }

    @Override
    public Long update(Asset asset) {
        asset.setModifyTime(new Date());
        return assetMapper.update(asset);
    }

    @Override
    public List<Asset> page(Map params) {
        return assetMapper.page(params);
    }

    @Override
    public List<Asset> list(Asset asset) {
        return assetMapper.list(asset);
    }

    @Override
    public Asset getAssetById(Long id) {
        return assetMapper.getById(id);
    }
}
