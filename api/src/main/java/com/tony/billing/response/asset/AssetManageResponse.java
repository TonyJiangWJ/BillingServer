package com.tony.billing.response.asset;

import com.tony.billing.dto.AssetManageDTO;
import com.tony.billing.model.AssetTypeModel;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * @author TonyJiang 2018/6/21
 */
public class AssetManageResponse extends BaseResponse {
    private AssetManageDTO assetManage;

    private List<AssetTypeModel> assetParentTypeList;

    private List<AssetTypeModel> liabilityParentTypeList;

    public AssetManageDTO getAssetManage() {
        return assetManage;
    }

    public void setAssetManage(AssetManageDTO assetManage) {
        this.assetManage = assetManage;
    }

    public List<AssetTypeModel> getAssetParentTypeList() {
        return assetParentTypeList;
    }

    public void setAssetParentTypeList(List<AssetTypeModel> assetParentTypeList) {
        this.assetParentTypeList = assetParentTypeList;
    }

    public List<AssetTypeModel> getLiabilityParentTypeList() {
        return liabilityParentTypeList;
    }

    public void setLiabilityParentTypeList(List<AssetTypeModel> liabilityParentTypeList) {
        this.liabilityParentTypeList = liabilityParentTypeList;
    }
}
