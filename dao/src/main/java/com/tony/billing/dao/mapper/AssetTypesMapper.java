package com.tony.billing.dao.mapper;

import com.tony.billing.entity.AssetTypes;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author TonyJiang 2018/6/21
 */
@Repository
public interface AssetTypesMapper {
    List<AssetTypes> listByCondition(AssetTypes assetTypes);

    Integer insert(AssetTypes assetTypes);

    Integer update(AssetTypes assetTypes);

    Integer deleteById(Map param);

    AssetTypes getById(Integer id);
}
