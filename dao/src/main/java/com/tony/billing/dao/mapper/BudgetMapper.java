package com.tony.billing.dao.mapper;

import com.tony.billing.entity.Budget;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author by TonyJiang on 2017/7/12.
 */
@Repository
public interface BudgetMapper {
    Long insert(Budget budget);

    Long update(Budget budget);

    Budget findByTagName(String tagName);

    List<Budget> findByYearMonth(Budget budget);
}
