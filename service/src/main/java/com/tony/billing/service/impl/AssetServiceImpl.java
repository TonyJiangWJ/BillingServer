package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumAssetParentType;
import com.tony.billing.constants.enums.EnumAssetType;
import com.tony.billing.dao.AssetDao;
import com.tony.billing.dto.AssetDTO;
import com.tony.billing.dto.AssetTypeDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.model.AssetModel;
import com.tony.billing.service.AssetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {

    @Resource
    private AssetDao assetDao;

    @Override
    public List<Asset> listAssetByUserId(Long userId) {
        Asset query = new Asset();
        query.setUserId(userId);
        return assetDao.list(query);
    }

    @Override
    public List<AssetModel> getAssetModelsByUserId(Long userId) {
        Asset query = new Asset();
        query.setUserId(userId);
        List<Asset> assetList = assetDao.list(query);
        List<AssetModel> assetModels = new ArrayList<>();
        for (Asset asset : assetList) {
            insertIntoModelList(assetModels, asset);
        }
        return assetModels;
    }

    @Override
    public AssetDTO getAssetInfoById(Long id) {
        Asset asset = assetDao.getAssetById(id);
        return new AssetDTO(asset);
    }

    @Override
    public boolean modifyAssetInfoById(Asset asset) {
        return assetDao.update(asset) > 0;
    }

    @Override
    public Long addAsset(Asset asset) {
        asset.setName(EnumAssetType.getEnumByType(asset.getType()).getDesc());
        return assetDao.insert(asset);
    }

    private void insertIntoModelList(List<AssetModel> assetModels, Asset asset) {
        boolean inserted = false;
        for (AssetModel assetModel : assetModels) {
            if (StringUtils.equals(asset.getParentType(), assetModel.getType())) {
                assetModel.getAssetList().add(new AssetDTO(asset));
                assetModel.setTotal(asset.getAmount() + assetModel.getTotal());
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            AssetModel assetModel = new AssetModel();
            assetModel.getAssetList().add(new AssetDTO(asset));
            assetModel.setType(asset.getParentType());
            assetModel.setName(EnumAssetParentType.getEnumByType(asset.getParentType()).getDesc());
            assetModel.setTotal(asset.getAmount() + assetModel.getTotal());
            assetModels.add(assetModel);
        }
    }

    @Override
    public List<AssetTypeDTO> getAssetTypesByParent(String parentType) {
        return null;
    }
}
