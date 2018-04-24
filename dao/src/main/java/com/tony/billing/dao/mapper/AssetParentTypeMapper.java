package com.tony.billing.dao.mapper;

import com.tony.billing.entity.AssetParentType;

import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 2018/04/24
 */
public interface AssetParentTypeMapper {
    Long insert(AssetParentType assetParentType);

    AssetParentType getById(Long id);

    Integer update(AssetParentType log);

    List<AssetParentType> list(AssetParentType condition);

    List<AssetParentType> page(Map params);
}
