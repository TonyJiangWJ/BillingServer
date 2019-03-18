package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.dao.mapper.AssetTypesMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.service.base.AbstractService;
import com.tony.billing.util.UserIdContainer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author TonyJiang 2018/6/21
 */
@Service
public class AssetTypesServiceImpl extends AbstractService<AssetTypes> implements AssetTypesService {


    @Resource
    private AssetTypesMapper assetTypesMapper;

    @Override
    protected AbstractMapper<AssetTypes> getMapper() {
        return assetTypesMapper;
    }

    @Override
    public List<AssetTypes> selectAssetTypeList() {
        return super.list(
                Stream.generate(() -> {
                    AssetTypes condition = new AssetTypes();
                    condition.setTypeIdentify(EnumTypeIdentify.ASSET.getIdentify());
                    condition.setUserId(UserIdContainer.getUserId());
                    return condition;
                }).findAny().get()
        );
    }

    @Override
    public List<AssetTypes> selectLiabilityTypeList() {
        return super.list(
                Stream.generate(() -> {
                    AssetTypes condition = new AssetTypes();
                    condition.setTypeIdentify(EnumTypeIdentify.LIABILITY.getIdentify());
                    condition.setUserId(UserIdContainer.getUserId());
                    return condition;
                }).findAny().get()
        );
    }

    @Override
    public List<AssetTypes> selectAssetTypeListByParent(String parentCode) {
        return super.list(
                Stream.generate(() -> {
                    AssetTypes condition = new AssetTypes();
                    condition.setParentCode(parentCode);
                    condition.setTypeIdentify(EnumTypeIdentify.ASSET.getIdentify());
                    condition.setUserId(UserIdContainer.getUserId());
                    return condition;
                }).findAny().get()
        );
    }

    @Override
    public List<AssetTypes> selectLiabilityTypeListByParent(String parentCode) {
        return super.list(
                Stream.generate(() -> {
                    AssetTypes condition = new AssetTypes();
                    condition.setParentCode(parentCode);
                    condition.setTypeIdentify(EnumTypeIdentify.LIABILITY.getIdentify());
                    condition.setUserId(UserIdContainer.getUserId());
                    return condition;
                }).findAny().get()
        );
    }

    @Override
    public List<AssetTypes> getAssetTypeByCondition(AssetTypes condition) {
        return super.list(condition);
    }

}
