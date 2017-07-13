package com.tony.billing.service;

import com.tony.billing.model.BudgetCostModel;
import com.tony.billing.entity.Budget;

import java.util.List;

/**
 * Author by TonyJiang on 2017/7/5.
 */
public interface BudgetService {
    Long saveBudget(Budget budget);

    List<Budget> queryBudgetsByCondition(Budget budget);

    List<BudgetCostModel> queryCostsByCondition(BudgetCostModel budget);
}
