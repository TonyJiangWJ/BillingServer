package com.tony.billing.dao;

import com.tony.billing.entity.Asset;

import java.util.List;
import java.util.Map;

public interface AssetDao {

    Long insert(Asset asset);

    Long update(Asset asset);

    List<Asset> page(Map params);

    List<Asset> list(Asset asset);

    Asset getAssetById(Long id);
}
