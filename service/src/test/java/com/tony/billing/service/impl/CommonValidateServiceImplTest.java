package com.tony.billing.service.impl;

import com.tony.billing.constraints.enums.EnumOwnershipCheckTables;
import com.tony.billing.service.api.CommonValidateService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author jiangwenjie 2019-03-20
 */
public class CommonValidateServiceImplTest extends BaseServiceTest {

    @Resource
    private CommonValidateService commonValidateService;

    @Test
    public void validateOwnership() {
        debugInfo(commonValidateService.validateOwnership(EnumOwnershipCheckTables.ASSET, 12L));
    }
}