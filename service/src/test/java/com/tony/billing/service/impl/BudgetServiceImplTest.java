package com.tony.billing.service.impl;

import com.tony.billing.entity.Budget;
import com.tony.billing.service.BudgetService;
import com.tony.billing.test.BaseServiceTestNoTransaction;
import com.tony.billing.util.UserIdContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.stream.Stream;

/**
 * @author jiangwenjie 2019-03-18
 */
public class BudgetServiceImplTest extends BaseServiceTestNoTransaction {

    @Resource
    private BudgetService budgetService;


    @Before
    public void setUp() throws Exception {
        UserIdContainer.setUserId(2L);
    }

    @After
    public void after() {
        UserIdContainer.removeUserId();
    }

    @Test
    public void saveBudget() {
        Budget budget = new Budget();
        budget.setUserId(UserIdContainer.getUserId());
        budget.setBudgetName("测试预算4");
        budget.setBelongYear("2019");
        budget.setBelongMonth(3);
        budget.setBudgetMoney(100000L);
        budgetService.insert(budget);
    }

    @Test
    public void queryBudgetsByCondition() {
        debugInfo(budgetService.queryBudgetsByCondition(Stream.generate(() -> {
            Budget budget = new Budget();
            budget.setBelongYear("2019");
            budget.setBelongMonth(3);
            budget.setUserId(UserIdContainer.getUserId());
            return budget;
        }).findFirst().get()));
    }

    @Test
    public void updateBudget() {
        Budget budget = new Budget();
        budget.setUserId(UserIdContainer.getUserId());
        budget.setBudgetName("测试预算4-change");
        budget.setBelongYear("2019");
        budget.setBelongMonth(3);
        budget.setBudgetMoney(120000L);
        budget.setId(4L);
        budget.setVersion(0);
        debugInfo(budgetService.updateBudget(budget));
    }

    @Test
    public void deleteBudget() {
    }

    @Test
    public void getById() {
        debugInfo(budgetService.getById(1L));
    }
}