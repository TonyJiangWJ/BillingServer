package com.tony.billing.service;

import com.tony.billing.entity.AssetTypes;

import java.util.List;

/**
 * @author TonyJiang 2018/6/21
 */
public interface AssetTypesService {

    List<AssetTypes> selectAssetTypeList();

    List<AssetTypes> selectLiabilityTypeList();

    List<AssetTypes> selectAssetTypeListByParent(String parentCode);

    List<AssetTypes> selectLiabilityTypeListByParent(String parentCode);

    List<AssetTypes> getAssetTypeByCondition(AssetTypes condition);

    Integer insert(AssetTypes assetTypes);

    boolean update(AssetTypes assetTypes);

    boolean deleteById(Integer id);

    AssetTypes selectById(Integer id);
}
