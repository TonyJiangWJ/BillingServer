package com.tony.billing.service.impl;

import com.tony.billing.service.CostReportService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangwj20966 8/6/2018
 */
public class CostReportServiceImplTest extends BaseServiceTest {
    @Autowired
    private CostReportService costReportService;

    @Test
    public void getMonthReportByDatePrefix() {
        List<String> datePrefix = new ArrayList<>();
        datePrefix.add("2018-04-25");
        datePrefix.add("2018-04-26");
        datePrefix.add("2018-04-27");
        debugInfo(costReportService.getReportByDatePrefix(datePrefix, 2L));
    }
}