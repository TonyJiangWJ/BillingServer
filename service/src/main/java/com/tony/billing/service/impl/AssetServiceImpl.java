package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.dao.AssetDao;
import com.tony.billing.dao.AssetTypesDao;
import com.tony.billing.dto.AssetDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.model.AssetModel;
import com.tony.billing.service.AssetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {

    @Resource
    private AssetDao assetDao;
    @Resource
    private AssetTypesDao assetTypesDao;

    @Override
    public List<Asset> listAssetByUserId(Long userId) {
        Asset query = new Asset();
        query.setUserId(userId);
        return assetDao.list(query);
    }

    @Override
    public List<AssetModel> getAssetModelsByUserId(Long userId) {
        assert userId != null;
        Asset query = new Asset();
        query.setUserId(userId);
        List<Asset> assetList = assetDao.list(query);
        List<AssetModel> assetModels = new ArrayList<>();
        AssetTypes assetTypes;
        Map<Integer, AssetTypes> assetTypesMap = new HashMap<>();
        Map<String, AssetTypes> parentTypesMap = new HashMap<>();
        for (Asset asset : assetList) {
            // 当type没有缓存从数据库中读取
            if (assetTypesMap.get(asset.getType()) == null) {
                assetTypes = assetTypesDao.selectById(asset.getType());
                if (assetTypes != null) {
                    assetTypesMap.put(asset.getType(), assetTypes);
                    // 当存在父级类别时缓存
                    if (StringUtils.isNotEmpty(assetTypes.getParentCode())) {
                        String parentCode = assetTypes.getParentCode();
                        if (parentTypesMap.get(parentCode) == null) {
                            assetTypes = assetTypesDao.getByTypeCode(parentCode, EnumTypeIdentify.ASSET.getIdentify(), userId);
                            if (assetTypes != null) {
                                parentTypesMap.put(assetTypes.getTypeCode(), assetTypes);
                            }
                        }
                    } else {
                        // 不存在父级类别时将自己作为父级，例如现金
                        if (parentTypesMap.get(assetTypes.getTypeCode()) == null) {
                            parentTypesMap.put(assetTypes.getTypeCode(), assetTypes);
                        }
                    }
                }
            }
        }
        Map<String, AssetModel> typeModel = new HashMap<>();
        AssetModel model;
        for (Map.Entry<String, AssetTypes> entry : parentTypesMap.entrySet()) {
            typeModel.put(entry.getKey(), new AssetModel(entry.getValue().getTypeDesc()));
        }
        for (Asset asset : assetList) {
            AssetTypes type = assetTypesMap.get(asset.getType());
            if (StringUtils.isNotEmpty(type.getParentCode())) {
                model = typeModel.get(type.getParentCode());
            } else {
                model = typeModel.get(type.getTypeCode());
            }
            model.setTotal(asset.getAmount() + model.getTotal());
            model.getAssetList().add(new AssetDTO(asset, type.getTypeDesc()));
        }
        for (Map.Entry<String, AssetModel> entry : typeModel.entrySet()) {
            assetModels.add(entry.getValue());
        }
        return assetModels;
    }

    @Override
    public Asset getAssetInfoById(Long id) {
        return assetDao.getAssetById(id);
    }

    @Override
    public boolean modifyAssetInfoById(Asset asset) {
        return assetDao.update(asset) > 0;
    }

    @Override
    public Long addAsset(Asset asset) {
        AssetTypes assetTypes = assetTypesDao.selectById(asset.getType());
        boolean isAssetTypeValid = assetTypes != null && (assetTypes.getUserId().equals(-1L) || assetTypes.getUserId().equals(asset.getUserId()));
        if (isAssetTypeValid) {
            if (StringUtils.isNotEmpty(asset.getExtName())) {
                asset.setName(asset.getExtName());
            } else {
                asset.setName(assetTypes.getTypeDesc());
            }
            return assetDao.insert(asset);
        } else {
            return -1L;
        }
    }

}
