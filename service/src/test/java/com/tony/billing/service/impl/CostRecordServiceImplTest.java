package com.tony.billing.service.impl;

import com.tony.billing.entity.PagerGrid;
import com.tony.billing.entity.query.CostRecordQuery;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jiangwenjie 2018-12-17
 */
public class CostRecordServiceImplTest extends BaseServiceTest {

    @Autowired
    private CostRecordService costRecordService;


    @Test
    public void page() {

        CostRecordQuery costRecordQuery = new CostRecordQuery();
        costRecordQuery.setContent("支付");
        costRecordQuery.setUserId(2L);
        PagerGrid<CostRecordQuery> pagerGrid = new PagerGrid<>(costRecordQuery);
        debug(costRecordService.page(pagerGrid));

    }
}