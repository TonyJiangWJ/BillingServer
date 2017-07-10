package com.tony.service;

import com.tony.entity.Budget;

import java.util.List;

/**
 * Author by TonyJiang on 2017/7/5.
 */
public interface BudgetService {
    Long saveBudget(Budget budget);

    List<Budget> queryBudgetsByCondition(Budget budget);

    List<Budget> queryCostsByCondition(Budget budget);
}
