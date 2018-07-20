package com.tony.billing.dao;

import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.query.CostRecordQuery;

import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 on 2017/6/2.
 */
public interface CostRecordDao {
    List<CostRecord> find(CostRecord record);

    List<CostRecordQuery> page(Map<String, Object> params);

    Integer count(Map<String, Object> params);

    CostRecord findByTradeNo(Map<String, Object> params);

    Long insert(CostRecord record);

    Integer toggleDeleteStatus(Map<String, Object> params);

    Integer toggleHideStatus(Map<String, Object> params);

    Integer updateByTradeNo(CostRecord record);

    List<CostRecord> findByTagId(Map<String, Object> params);
}
