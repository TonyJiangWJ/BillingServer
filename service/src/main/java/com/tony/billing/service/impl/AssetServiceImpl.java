package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.constants.enums.EnumYesOrNo;
import com.tony.billing.dao.mapper.AssetMapper;
import com.tony.billing.dao.mapper.AssetTypesMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.dto.AssetDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.model.AssetModel;
import com.tony.billing.service.AssetService;
import com.tony.billing.service.base.AbstractService;
import com.tony.billing.util.UserIdContainer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class AssetServiceImpl extends AbstractService<Asset> implements AssetService {

    @Resource
    private AssetMapper assetMapper;
    @Resource
    private AssetTypesMapper assetTypesMapper;

    @Override
    protected AbstractMapper<Asset> getMapper() {
        return assetMapper;
    }

    @Override
    public List<Asset> listAssetByUserId(Long userId) {
        Asset query = new Asset();
        query.setUserId(userId);
        return super.list(query);
    }

    @Override
    public List<AssetModel> getAssetModelsByUserId(Long userId) {
        assert userId != null;
        Asset query = new Asset();
        query.setUserId(userId);
        List<Asset> assetList = super.list(query);
        List<AssetModel> assetModels = new ArrayList<>();
        AssetTypes assetTypes;
        Map<Long, AssetTypes> assetTypesMap = new HashMap<>();
        Map<String, AssetTypes> parentTypesMap = new HashMap<>();
        for (Asset asset : assetList) {
            // 当type没有缓存从数据库中读取
            if (assetTypesMap.get(asset.getType()) == null) {
                assetTypes = assetTypesMapper.getById(asset.getType(), userId);
                if (assetTypes != null) {
                    assetTypesMap.put(asset.getType(), assetTypes);
                    // 当存在父级类别时缓存
                    if (StringUtils.isNotEmpty(assetTypes.getParentCode())) {
                        String parentCode = assetTypes.getParentCode();
                        if (parentTypesMap.get(parentCode) == null) {
                            List<AssetTypes> records = assetTypesMapper.list(Stream.generate(() -> {
                                AssetTypes condition = new AssetTypes();
                                condition.setTypeIdentify(EnumTypeIdentify.ASSET.getIdentify());
                                condition.setTypeCode(parentCode);
                                condition.setUserId(userId);
                                return condition;
                            }).findAny().get());

                            if (CollectionUtils.isNotEmpty(records)) {
                                assetTypes = records.get(0);
                                parentTypesMap.put(assetTypes.getTypeCode(), assetTypes);
                            }
                        }
                    } else {
                        // 不存在父级类别时将自己作为父级，例如现金
                        parentTypesMap.putIfAbsent(assetTypes.getTypeCode(), assetTypes);
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
            // 当前可使用的总额
            if (EnumYesOrNo.YES.getCode().equals(asset.getAvailable())) {
                model.setTotalAvailable(asset.getAmount() + model.getTotalAvailable());
            }
            model.getAssetList().add(new AssetDTO(asset, type.getTypeDesc()));
        }
        for (Map.Entry<String, AssetModel> entry : typeModel.entrySet()) {
            assetModels.add(entry.getValue());
        }
        return assetModels;
    }

    @Override
    public Asset getAssetInfoById(Long id) {
        return super.getById(id);
    }

    @Override
    public boolean modifyAssetInfoById(Asset asset) {
        return super.update(asset);
    }

    @Override
    public Long addAsset(Asset asset) {
        asset.setUserId(UserIdContainer.getUserId());
        AssetTypes assetTypes = assetTypesMapper.getById(asset.getType(), asset.getUserId());
        boolean isAssetTypeValid = assetTypes != null && (assetTypes.getUserId().equals(-1L) || assetTypes.getUserId().equals(asset.getUserId()));
        if (isAssetTypeValid) {
            if (StringUtils.isNotEmpty(asset.getExtName())) {
                asset.setName(asset.getExtName());
            } else {
                asset.setName(assetTypes.getTypeDesc());
            }
            return super.insert(asset);
        } else {
            return -1L;
        }
    }

    @Override
    public boolean deleteAsset(Long assetId) {
        return super.deleteById(assetId);
    }

}
