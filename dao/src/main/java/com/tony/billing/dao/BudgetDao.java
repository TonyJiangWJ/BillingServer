package com.tony.billing.dao;

import com.tony.billing.entity.Budget;

import java.util.List;

/**
 * Author by TonyJiang on 2017/7/5.
 */
public interface BudgetDao {
    Long insert(Budget budget);

    Long update(Budget budget);

    Budget findByTagName(String tagName);

    List<Budget> findByYearMonth(Budget budget);
}
