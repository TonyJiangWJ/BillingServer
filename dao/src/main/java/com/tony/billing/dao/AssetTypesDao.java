package com.tony.billing.dao;

import com.tony.billing.entity.AssetTypes;

import java.util.List;

/**
 * @author TonyJiang 2018/6/21
 */
public interface AssetTypesDao {

    /**
     * 根据父类型获取所有子类
     *
     * @param parentType
     * @param typeIdentify
     * @return
     */
    List<AssetTypes> listTypesByParentType(String parentType, String typeIdentify, Long userId);

    List<AssetTypes> listParentTypes(String typeIdentify, Long userId);

    AssetTypes getByTypeCode(String typeCode, String typeIdentify, Long userId);

    Integer insert(AssetTypes assetTypes);

    boolean update(AssetTypes assetTypes);

    boolean deleteById(Integer id, Long userId);

    List<AssetTypes> listByCondition(AssetTypes assetTypes);
}
