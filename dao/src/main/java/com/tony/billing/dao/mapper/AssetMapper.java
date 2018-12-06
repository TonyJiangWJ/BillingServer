package com.tony.billing.dao.mapper;

import com.tony.billing.entity.Asset;

import java.util.List;
import java.util.Map;

public interface AssetMapper {

    Long insert(Asset asset);

    Long update(Asset asset);

    List<Asset> page(Map params);

    List<Asset> list(Asset asset);

    Asset getById(Long id);

    Integer deleteById(Map params);
}
