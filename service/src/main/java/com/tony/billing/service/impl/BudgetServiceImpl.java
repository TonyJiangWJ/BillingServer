package com.tony.billing.service.impl;

import com.tony.billing.dao.BudgetDao;
import com.tony.billing.dao.CostRecordDao;
import com.tony.billing.dto.BudgetCostDto;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.service.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/7/8.
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    private Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);
    @Resource
    private BudgetDao budgetDao;
    @Resource
    private CostRecordDao costRecordDao;

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
        return budgetDao.findByYearMonth(budget);
    }

    @Override
    public List<BudgetCostDto> queryCostsByCondition(Budget budget) {
        if(budget.getUserId()!=null) {
            List<Budget> budgets = budgetDao.findByYearMonth(budget);
            Map<String, Object> params = null;
            for (Budget entity : budgets) {
                params = new HashMap<>();
                params.put("tagId", entity.getTagId());
                params.put("userId", budget.getUserId());
                List<CostRecord> costs = costRecordDao.findByTagId(params);
            }
        }else{

        }
        return null;
    }
}
