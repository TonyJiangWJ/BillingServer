package com.tony.billing.service;

import com.tony.billing.entity.AssetTypes;

import java.util.List;

/**
 * @author TonyJiang 2018/6/21
 */
public interface AssetTypesService {

    List<AssetTypes> selectAssetTypeList(Long userId);

    List<AssetTypes> selectLiabilityTypeList(Long userId);

    List<AssetTypes> selectAssetTypeListByParent(String parentCode, Long userId);

    List<AssetTypes> selectLiabilityTypeListByParent(String parentCode, Long userId);

    List<AssetTypes> getAssetTypeByCondition(AssetTypes condition);

    Integer insert(AssetTypes assetTypes);

    boolean update(AssetTypes assetTypes);

    boolean deleteById(Integer id, Long userId);

    AssetTypes selectById(Integer id, Long userId);
}
