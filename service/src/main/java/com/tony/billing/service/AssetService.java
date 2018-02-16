package com.tony.billing.service;

import com.tony.billing.entity.Asset;
import com.tony.billing.model.AssetModel;

import java.util.List;

public interface AssetService {

    List<Asset> listAssetByUserId(Long userId);

    List<AssetModel> getAssetModelsByUserId(Long userId);
}
