package com.tony.billing.service.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.dao.AssetTypesDao;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.service.AssetTypesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author TonyJiang 2018/6/21
 */
@Service
public class AssetTypesServiceImpl implements AssetTypesService {

    @Resource
    private AssetTypesDao assetTypesDao;

    @Override
    public List<AssetTypes> selectAssetTypeList(Long userId) {
        return assetTypesDao.listParentTypes(EnumTypeIdentify.ASSET.getIdentify(), userId);
    }

    @Override
    public List<AssetTypes> selectLiabilityTypeList(Long userId) {
        return assetTypesDao.listParentTypes(EnumTypeIdentify.LIABILITY.getIdentify(), userId);
    }

    @Override
    public List<AssetTypes> selectAssetTypeListByParent(String parentCode, Long userId) {
        return assetTypesDao.listTypesByParentType(parentCode, EnumTypeIdentify.ASSET.getIdentify(), userId);
    }

    @Override
    public List<AssetTypes> selectLiabilityTypeListByParent(String parentCode, Long userId) {
        return assetTypesDao.listTypesByParentType(parentCode, EnumTypeIdentify.LIABILITY.getIdentify(), userId);
    }

    @Override
    public Integer insert(AssetTypes assetTypes) {
        Preconditions.checkNotNull(assetTypes.getUserId(), "userId不能为空");
        Preconditions.checkNotNull(assetTypes.getTypeCode());
        Preconditions.checkNotNull(assetTypes.getTypeDesc());
        return assetTypesDao.insert(assetTypes);
    }

    @Override
    public boolean update(AssetTypes assetTypes) {
        return assetTypesDao.update(assetTypes);
    }

    @Override
    public boolean deleteById(Integer id, Long userId) {
        return assetTypesDao.deleteById(id, userId);
    }
}
