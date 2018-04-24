package com.tony.billing.response.asset;

import com.tony.billing.dto.AssetTypeDTO;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * @author jiangwj20966 2018/4/24
 */
public class AssetTypeResponse extends BaseResponse {
    private List<AssetTypeDTO> assetTypes;

    public List<AssetTypeDTO> getAssetTypes() {
        return assetTypes;
    }

    public void setAssetTypes(List<AssetTypeDTO> assetTypes) {
        this.assetTypes = assetTypes;
    }
}
