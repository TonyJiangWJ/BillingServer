package com.tony.billing.service.impl;

import com.tony.billing.entity.Liability;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.Date;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/2/16
 */
public class LiabilityServiceImplTest extends BaseServiceTest {

    @Resource
    private LiabilityService liabilityService;

    @Test
    public void getLiabilityModelsByUserId() {
        long start = System.currentTimeMillis();
        debugInfo(liabilityService.getLiabilityModelsByUserId(2L));
        long end = System.currentTimeMillis();
        logger.info("cost time:{}", end-start);
    }

    @Test
    public void getMonthLiabilityModelsByUserId() {
        debugInfo(liabilityService.getMonthLiabilityModelsByUserId(2L));
    }

    @Test
    public void insertLiability() throws SQLException {
        Liability liability = new Liability();
        liability.setAmount(123010L);
        liability.setInstallment(12);
        liability.setRepaymentDay(new Date());
        liability.setUserId(1L);


        liabilityService.createLiabilityInfo(liability);

        debugInfo(liabilityService.getMonthLiabilityModelsByUserId(1L));
    }
}