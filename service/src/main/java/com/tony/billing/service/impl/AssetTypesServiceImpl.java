package com.tony.billing.service.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.dao.AssetTypesDao;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.util.UserIdContainer;
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
    public List<AssetTypes> selectAssetTypeList() {
        return assetTypesDao.listParentTypes(EnumTypeIdentify.ASSET.getIdentify(), UserIdContainer.getUserId());
    }

    @Override
    public List<AssetTypes> selectLiabilityTypeList() {
        return assetTypesDao.listParentTypes(EnumTypeIdentify.LIABILITY.getIdentify(), UserIdContainer.getUserId());
    }

    @Override
    public List<AssetTypes> selectAssetTypeListByParent(String parentCode) {
        return assetTypesDao.listTypesByParentType(parentCode, EnumTypeIdentify.ASSET.getIdentify(), UserIdContainer.getUserId());
    }

    @Override
    public List<AssetTypes> selectLiabilityTypeListByParent(String parentCode) {
        return assetTypesDao.listTypesByParentType(parentCode, EnumTypeIdentify.LIABILITY.getIdentify(), UserIdContainer.getUserId());
    }

    @Override
    public List<AssetTypes> getAssetTypeByCondition(AssetTypes condition) {
        return assetTypesDao.listByCondition(condition);
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
    public boolean deleteById(Integer id) {
        return assetTypesDao.deleteById(id, UserIdContainer.getUserId());
    }

    @Override
    public AssetTypes selectById(Integer id) {
        AssetTypes assetTypes = assetTypesDao.selectById(id);
        if (assetTypes != null && (assetTypes.getUserId().equals(-1L) || assetTypes.getUserId().equals(UserIdContainer.getUserId()))) {
            return assetTypes;
        }
        return null;
    }
}
