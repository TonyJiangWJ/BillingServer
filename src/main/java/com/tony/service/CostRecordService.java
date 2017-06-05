package com.tony.service;

import com.tony.entity.CostRecord;
import com.tony.entity.PagerGrid;

import java.util.List;
import java.util.Map;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
public interface CostRecordService {
    List<CostRecord> find(CostRecord record);

    PagerGrid<CostRecord> page(PagerGrid<CostRecord> pagerGrid);

    CostRecord findByTradeNo(String tradeNo);

    Integer toggleDeleteStatus(Map<String, Object> params);

    Long orderPut(CostRecord record);
}
