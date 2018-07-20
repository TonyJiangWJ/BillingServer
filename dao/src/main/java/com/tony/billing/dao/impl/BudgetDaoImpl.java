package com.tony.billing.dao.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.dao.BudgetDao;
import com.tony.billing.dao.mapper.BudgetMapper;
import com.tony.billing.entity.Budget;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author by TonyJiang on 2017/7/12.
 */
@Service("budgetDao")
public class BudgetDaoImpl implements BudgetDao {

    @Resource
    private BudgetMapper budgetMapper;

    @Override
    public Long insert(Budget budget) {
        Preconditions.checkNotNull(budget.getBelongMonth(), "month must not be null");
        Preconditions.checkNotNull(budget.getBelongYear(), "year must not be null");
        Preconditions.checkNotNull(budget.getTagId(), "tagId must not be null");
        Preconditions.checkNotNull(budget.getUserId(), "userId must not be null");
        return budgetMapper.insert(budget);
    }

    @Override
    public Long update(Budget budget) {
        Preconditions.checkNotNull(budget.getId(), "id must not be null");
        return budgetMapper.update(budget);
    }

    @Override
    public Budget findByTagName(String tagName) {
        return budgetMapper.findByTagName(tagName);
    }

    @Override
    public List<Budget> findByYearMonth(Budget budget) {
        Preconditions.checkNotNull(budget.getUserId(), "userId must not be null");
        Preconditions.checkNotNull(budget.getBelongMonth(), "month must not be null");
        Preconditions.checkNotNull(budget.getBelongYear(), "year must not be null");
        return budgetMapper.findByYearMonth(budget);
    }
}
