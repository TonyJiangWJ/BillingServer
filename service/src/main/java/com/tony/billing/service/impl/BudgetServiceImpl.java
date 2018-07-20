package com.tony.billing.service.impl;

import com.tony.billing.dao.BudgetDao;
import com.tony.billing.dao.CostRecordDao;
import com.tony.billing.dao.TagInfoDao;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.model.BudgetCostModel;
import com.tony.billing.model.BudgetModel;
import com.tony.billing.service.BudgetService;
import com.tony.billing.util.BeanCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author by TonyJiang on 2017/7/8.
 */
@Service
public class BudgetServiceImpl implements BudgetService {

    private Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);
    @Resource
    private BudgetDao budgetDao;
    @Resource
    private CostRecordDao costRecordDao;
    @Resource
    private TagInfoDao tagInfoDao;

    @Override
    public Long saveBudget(Budget budget) {
        budget.setCreateTime(new Date());
        budget.setModifyTime(budget.getCreateTime());
        long flag = budgetDao.insert(budget);
        if (flag > 0) {
            return budget.getId();
        }
        return -1L;
    }

    @Override
    public List<BudgetModel> queryBudgetsByCondition(Budget budget) {
        List<Budget> budgets = budgetDao.findByYearMonth(budget);
        List<BudgetModel> budgetModels = new ArrayList<>();
        for (Budget budget1 : budgets) {
            budgetModels.add(BeanCopyUtil.copy(budget1, BudgetModel.class));
        }
        return budgetModels;
    }

    @Override
    public List<BudgetCostModel> queryCostsByCondition(BudgetCostModel budget) {
        if (budget.getUserId() != null) {
            List<Budget> budgets = budgetDao.findByYearMonth(budget);
            Map<String, Object> params;
            BudgetCostModel budgetCostModel;

            Long totalCost, costExpHidden, costExpDelete, costClear;
            if (CollectionUtils.isNotEmpty(budgets)) {
                List<BudgetCostModel> budgetCostModelList = new ArrayList<>();
                for (Budget entity : budgets) {
                    params = new HashMap<>();
                    params.put("tagId", entity.getTagId());
                    params.put("userId", budget.getUserId());
                    List<CostRecord> costs = costRecordDao.findByTagId(params);
                    if (CollectionUtils.isNotEmpty(costs)) {
                        String tagName = tagInfoDao.getTagInfoById(entity.getTagId()).getTagName();
                        budgetCostModel = new BudgetCostModel();
                        budgetCostModel.setBudgetMoney(entity.getBudgetMoney());
                        budgetCostModel.setBelongMonth(entity.getBelongMonth());
                        budgetCostModel.setBelongYear(entity.getBelongYear());
                        budgetCostModel.setTagName(tagName);

                        totalCost = costExpHidden = costExpDelete = costClear = 0L;

                        for (CostRecord cost : costs) {
                            switch (cost.getIsDelete() * 10 + cost.getIsHidden()) {
                                case 0:
                                    totalCost += cost.getMoney();
                                    break;
                                case 1:
                                    costExpDelete += cost.getMoney();
                                    break;
                                case 10:
                                    costExpHidden += cost.getMoney();
                                    break;
                                case 11:
                                    costClear += cost.getMoney();
                                    break;
                                default:
                                    logger.error("this is impossible");
                            }
                            totalCost += cost.getMoney();
                        }
                        budgetCostModel.setTotalCost(totalCost);
                        budgetCostModel.setRestMoney(entity.getBudgetMoney() - totalCost);
                        budgetCostModel.setCostClear(costClear);
                        budgetCostModel.setCostExpDelete(costExpDelete);
                        budgetCostModel.setCostExpHidden(costExpHidden);
                        budgetCostModelList.add(budgetCostModel);
                    }
                }
                return budgetCostModelList;
            }
        } else {
            logger.error("User id is null");
        }
        return null;
    }
}
