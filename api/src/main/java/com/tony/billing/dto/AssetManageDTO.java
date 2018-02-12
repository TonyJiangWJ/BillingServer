package com.tony.billing.dto;

import com.tony.billing.model.AssetModel;
import com.tony.billing.model.LiabilityModel;

import java.util.List;

/**
 * @author TonyJiang on 2018/2/12
 */
public class AssetManageDTO {

    private Long totalAsset;
    private Long totalLiability;
    private Long cleanAsset;// 净资产
    private List<AssetModel> assetModels;
    private List<LiabilityModel> liabilityModels;

    public Long getTotalAsset() {
        return totalAsset;
    }

    public void setTotalAsset(Long totalAsset) {
        this.totalAsset = totalAsset;
    }

    public Long getTotalLiability() {
        return totalLiability;
    }

    public void setTotalLiability(Long totalLiability) {
        this.totalLiability = totalLiability;
    }

    public Long getCleanAsset() {
        return cleanAsset;
    }

    public void setCleanAsset(Long cleanAsset) {
        this.cleanAsset = cleanAsset;
    }

    public List<AssetModel> getAssetModels() {
        return assetModels;
    }

    public void setAssetModels(List<AssetModel> assetModels) {
        this.assetModels = assetModels;
    }

    public List<LiabilityModel> getLiabilityModels() {
        return liabilityModels;
    }

    public void setLiabilityModels(List<LiabilityModel> liabilityModels) {
        this.liabilityModels = liabilityModels;
    }
}
