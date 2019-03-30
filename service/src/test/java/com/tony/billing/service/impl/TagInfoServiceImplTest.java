package com.tony.billing.service.impl;

import com.tony.billing.dao.mapper.BudgetMapper;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.TagBudgetRef;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.service.TagInfoService;
import com.tony.billing.test.BaseServiceTestNoTransaction;
import com.tony.billing.util.UserIdContainer;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiangwenjie 2019-03-18
 */
public class TagInfoServiceImplTest extends BaseServiceTestNoTransaction {

    @Resource
    private TagInfoService tagInfoService;

    @Resource
    private TagInfoMapper tagInfoMapper;
    @Resource
    private BudgetMapper budgetMapper;


    @Test
    public void putTagInfo() {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setUserId(UserIdContainer.getUserId());
        tagInfo.setTagName("testNameNew");
        debugInfo(tagInfoService.putTagInfo(tagInfo));
    }

    @Test
    public void insertTagBudgetRef() {
        Budget budget = new Budget();
        budget.setUserId(UserIdContainer.getUserId());
        List<Budget> budgets = budgetMapper.list(budget);
        TagInfo condition = new TagInfo();
        condition.setUserId(UserIdContainer.getUserId());
        List<TagInfo> tagInfoList = tagInfoMapper.list(condition);
        budgets.forEach(b -> {
            TagBudgetRef ref = new TagBudgetRef();
            ref.setBudgetId(b.getId());
            ref.setTagId(tagInfoList.get((int) (Math.random() * 100 % tagInfoList.size())).getId());
            debugInfo("inserted budgetTagRef id:{}", tagInfoService.insertTagBudgetRef(ref));
        });
    }

    @Test
    public void loopRandomInsert() {
        int i=5;
        while(i-->0) {
            insertTagBudgetRef();
        }
    }

    @Test
    public void listTagInfoByBudgetId() {
    }
}