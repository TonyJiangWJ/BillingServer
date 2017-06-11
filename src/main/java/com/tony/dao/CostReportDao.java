package com.tony.dao;

import com.tony.entity.SimpleReportEntity;
import com.tony.entity.query.ReportEntityQuery;
import org.springframework.stereotype.Repository;

/**
 * Author by TonyJiang on 2017/6/10.
 */
@Repository
public interface CostReportDao {

    Long getReportAmountByCondition(ReportEntityQuery query);
}
