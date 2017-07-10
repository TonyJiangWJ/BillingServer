package com.tony.service.impl;

import com.tony.dao.BudgetDao;
import com.tony.entity.Budget;
import com.tony.service.BudgetService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Author by TonyJiang on 2017/7/8.
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    @Resource
    private BudgetDao budgetDao;

    @Override
    public Long saveBudget(Budget budget) {
        budget.setCreateTime(new Date());
        budget.setModifyTime(budget.getCreateTime());
        long flag = budgetDao.insert(budget);
        if(flag>0){
            return budget.getId();
        }
        return -1L;
    }

    @Override
    public List<Budget> queryBudgetsByCondition(Budget budget) {
        return null;
    }

    @Override
    public List<Budget> queryCostsByCondition(Budget budget) {
        return null;
    }
}
