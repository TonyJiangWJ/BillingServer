package com.tony.dao;

import com.tony.entity.CostRecord;
import com.tony.entity.PagerGrid;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
@Repository
public interface CostRecordDao {
    List<CostRecord> find(CostRecord record);

    List<CostRecord> page(Map<String,Object> param);

    Integer count(Map<String, Object> params);

    CostRecord findByTradeNo(String tradeNo);

    Long insert(CostRecord record);

    Integer toggleDeleteStatus(Map<String, Object> params);

    Integer updateByTradeNo(CostRecord record);
}
