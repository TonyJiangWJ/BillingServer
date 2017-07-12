package com.tony.billing.service;

import com.tony.billing.entity.ReportEntity;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/11.
 */
public interface CostReportService {
    List<ReportEntity> getMonthReportByMonth(List<String> month, Long userId);
}
