package com.tony.billing.service.impl;

import com.tony.billing.dao.CostReportDao;
import com.tony.billing.entity.RawReportEntity;
import com.tony.billing.entity.ReportEntity;
import com.tony.billing.entity.query.ReportEntityQuery;
import com.tony.billing.service.CostReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by TonyJiang on 2017/6/11.
 */
@Service
public class CostReportServiceImpl implements CostReportService {

    @Resource
    private CostReportDao costReportDao;

    @Override
    public List<ReportEntity> getReportByDatePrefix(List<String> datePrefixes, Long userId) {
        List<ReportEntity> reportList = new ArrayList<>();
        for (String datePrefix : datePrefixes) {
            reportList.add(getReportInfo(datePrefix, userId));
        }
        return reportList;
    }

    private ReportEntity getReportInfo(String datePrefix, Long userId) {

        ReportEntityQuery query = new ReportEntityQuery();
        query.setDatePrefix(datePrefix);
        query.setUserId(userId);
        RawReportEntity reportEntity = costReportDao.getReportTypeAmountByCondition(query);
        if (reportEntity != null) {
            return new ReportEntity(datePrefix, reportEntity);
        } else {
            return null;
        }
    }

}
