package com.tony.billing.service.impl;

import com.tony.billing.service.LiabilityService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;

import javax.annotation.Resource;

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
}