package com.tony.service;

import com.tony.entity.CostRecord;

import java.util.List;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
public interface CostRecordService {
    List<CostRecord> find(CostRecord record);
}
