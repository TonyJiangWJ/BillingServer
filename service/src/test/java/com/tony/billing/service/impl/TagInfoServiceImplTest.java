package com.tony.billing.service.impl;

import com.tony.billing.entity.TagBudgetRef;
import com.tony.billing.service.TagInfoService;
import com.tony.billing.test.BaseServiceTest;
import com.tony.billing.test.BaseServiceTestNoTransaction;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author jiangwenjie 2019-03-18
 */
public class TagInfoServiceImplTest extends BaseServiceTestNoTransaction {

    @Resource
    private TagInfoService tagInfoService;



    @Test
    public void insertTagBudgetRef() {
        TagBudgetRef ref = new TagBudgetRef();
        ref.setBudgetId(1L);
        ref.setTagId(6L);
        tagInfoService.insertTagBudgetRef(ref);
    }

    @Test
    public void listTagInfoByBudgetId() {
    }
}