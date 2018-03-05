package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumLiabilityParentType;
import com.tony.billing.constants.enums.EnumLiabilityType;
import com.tony.billing.entity.Liability;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;

import javax.annotation.Resource;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/2/16
 */
public class LiabilityServiceImplTest extends BaseServiceTest{

    @Resource
    private LiabilityService liabilityService;

    @Test
    public void listLiabilityByUserId() {
        debug(liabilityService.listLiabilityByUserId(2L));
    }

    @Test
    public void getLiabilityModelsByUserId() {
        debug(liabilityService.getLiabilityModelsByUserId(2L));
    }

    @Test
    public void getMonthLiabilityModelsByUserId() {
        debug(liabilityService.getMonthLiabilityModelsByUserId(2L));
    }

    @Test
    public void insertLiability() throws SQLException {
        Liability liability = new Liability();
        liability.setAmount(123010L);
        liability.setInstallment(12);
        liability.setRepaymentDay(new Date());
        liability.setUserId(1L);
        liability.setType(EnumLiabilityType.ALIPAY_HUABEI.getType());
        liability.setParentType(EnumLiabilityParentType.ALIPAY.getType());

        liabilityService.createLiabilityInfo(liability);

        debug(liabilityService.getMonthLiabilityModelsByUserId(1L));
    }

    @Test
    public void listLiabilityByParent() throws Exception {
        debug(liabilityService.getLiabilityTypesByParent("1"));
    }
}