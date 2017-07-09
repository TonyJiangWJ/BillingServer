package com.tony.dao;

import com.tony.entity.Budget;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Author by TonyJiang on 2017/7/5.
 */
@Repository
public interface BudgetDao {
    Long insert(Budget budget);

    Long update(Budget budget);

    Budget findByTagName(String tagName);

    List<Budget> findByYearMonth(Budget budget);
}
