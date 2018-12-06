package com.tony.billing.request.asset;

import com.tony.billing.request.BaseRequest;

/**
 * @author jiangwenjie 2018-11-25
 */
public class AssetDeleteRequest extends BaseRequest {
    private Long assetId;

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }
}
