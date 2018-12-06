package com.tony.billing.dao;

import com.tony.billing.entity.RawReportEntity;
import com.tony.billing.entity.query.ReportEntityQuery;

/**
 * @author by TonyJiang on 2017/6/10.
 */
public interface CostReportDao {

    /**
     * 获取收支报表
     * @param query 日期及用户信息
     * @return 给定日期的收支情况
     */
    RawReportEntity getReportTypeAmountByCondition(ReportEntityQuery query);
}
