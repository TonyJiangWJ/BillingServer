package com.tony.billing.dao.impl;

import com.tony.billing.dao.CostReportDao;
import com.tony.billing.dao.mapper.CostReportMapper;
import com.tony.billing.entity.query.ReportEntityQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Author by TonyJiang on 2017/7/12.
 */
@Service("costReportDao")
public class CostReportDaoImpl implements CostReportDao {

    @Resource
    private CostReportMapper costReportMapper;

    @Override
    public Long getReportAmountByCondition(ReportEntityQuery query) {
        return costReportMapper.getReportAmountByCondition(query);
    }
}
