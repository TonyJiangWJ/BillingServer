package com.tony.billing.dao.impl;

import com.tony.billing.dao.AssetTypesDao;
import com.tony.billing.dao.mapper.AssetTypesMapper;
import com.tony.billing.entity.AssetTypes;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TonyJiang 2018/6/21
 */
@Repository
public class AssetTypesDaoImpl implements AssetTypesDao {

    @Resource
    private AssetTypesMapper assetTypesMapper;

    @Override
    public List<AssetTypes> listTypesByParentType(String parentType, String typeIdentify, Long userId) {
        AssetTypes query = new AssetTypes();
        query.setTypeIdentify(typeIdentify);
        query.setParentCode(parentType);
        query.setUserId(userId);
        return assetTypesMapper.listByCondition(query);
    }

    @Override
    public List<AssetTypes> listParentTypes(String typeIdentify, Long userId) {
        AssetTypes query = new AssetTypes();
        query.setTypeIdentify(typeIdentify);
        query.setUserId(userId);
        return assetTypesMapper.listByCondition(query);
    }

    @Override
    public AssetTypes getByTypeCode(String typeCode, String typeIdentify, Long userId) {
        AssetTypes query = new AssetTypes();
        query.setTypeIdentify(typeIdentify);
        query.setTypeCode(typeCode);
        query.setUserId(userId);
        List<AssetTypes> results = assetTypesMapper.listByCondition(query);
        if (CollectionUtils.isNotEmpty(results)) {
            return results.get(0);
        }
        return null;
    }

    @Override
    public Integer insert(AssetTypes assetTypes) {
        AssetTypes query = new AssetTypes();
        query.setTypeIdentify(assetTypes.getTypeIdentify());
        query.setTypeCode(assetTypes.getTypeCode());
        query.setParentCode(assetTypes.getParentCode());
        query.setUserId(assetTypes.getUserId());
        List<AssetTypes> existing = assetTypesMapper.listByCondition(query);
        if (CollectionUtils.isNotEmpty(existing)) {
            return -1;
        }
        assetTypes.setCreateTime(new Date());
        assetTypes.setModifyTime(new Date());
        return assetTypesMapper.insert(assetTypes);
    }

    @Override
    public boolean update(AssetTypes assetTypes) {
        assetTypes.setModifyTime(new Date());
        return assetTypesMapper.update(assetTypes) > 0;
    }

    @Override
    public boolean deleteById(Integer id, Long userId) {
        Map<String, Object> param = new HashMap<>(2);
        param.put("id", id);
        param.put("userId", userId);
        return assetTypesMapper.deleteById(param) > 0;
    }

    @Override
    public List<AssetTypes> listByCondition(AssetTypes assetTypes) {
        return assetTypesMapper.listByCondition(assetTypes);
    }
}
