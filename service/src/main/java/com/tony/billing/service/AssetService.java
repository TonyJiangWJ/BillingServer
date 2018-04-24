package com.tony.billing.service;

import com.tony.billing.dto.AssetDTO;
import com.tony.billing.dto.AssetTypeDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.model.AssetModel;

import java.util.List;

public interface AssetService {

    List<Asset> listAssetByUserId(Long userId);

    List<AssetModel> getAssetModelsByUserId(Long userId);

    /**
     * 根据id获取资产信息
     *
     * @param id
     * @return
     */
    AssetDTO getAssetInfoById(Long id);

    /**
     * 修改资产信息
     *
     * @param asset
     * @return
     */
    boolean modifyAssetInfoById(Asset asset);

    /**
     * 创建资产信息
     *
     * @param asset
     * @return
     */
    Long addAsset(Asset asset);

    /**
     * 根据父类型获取子类型列表
     *
     * @param parentType 父类型
     * @return
     */
    List<AssetTypeDTO> getAssetTypesByParent(String parentType);
}
