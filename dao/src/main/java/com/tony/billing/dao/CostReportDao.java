package com.tony.billing.dao;

import com.tony.billing.entity.query.ReportEntityQuery;

/**
 * @author by TonyJiang on 2017/6/10.
 */
public interface CostReportDao {

    Long getReportAmountByCondition(ReportEntityQuery query);
}
