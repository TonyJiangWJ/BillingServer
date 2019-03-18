package com.tony.billing.service.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.dao.mapper.BudgetMapper;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.dto.BudgetDTO;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.functions.TagInfoToDtoListSupplier;
import com.tony.billing.service.BudgetService;
import com.tony.billing.service.base.AbstractService;
import com.tony.billing.util.MoneyUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author by TonyJiang on 2017/7/8.
 */
@Service
public class BudgetServiceImpl extends AbstractService<Budget> implements BudgetService {

    private Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);
    @Resource
    private BudgetMapper budgetMapper;
    @Resource
    private TagInfoMapper tagInfoMapper;

    @Override
    protected AbstractMapper<Budget> getMapper() {
        return budgetMapper;
    }

    @Override
    public Long insert(Budget budget) {
        Preconditions.checkState(StringUtils.isNotEmpty(budget.getBudgetName()), "");
        Preconditions.checkNotNull(budget.getBelongMonth(), "month must not be null");
        Preconditions.checkNotNull(budget.getBelongYear(), "year must not be null");
        Preconditions.checkNotNull(budget.getUserId(), "userId must not be null");
        return super.insert(budget);
    }

    @Override
    public List<BudgetDTO> queryBudgetsByCondition(Budget condition) {
        Preconditions.checkNotNull(condition.getUserId(), "userId must not be null");
        Preconditions.checkNotNull(condition.getBelongMonth(), "month must not be null");
        Preconditions.checkNotNull(condition.getBelongYear(), "year must not be null");
        List<Budget> budgets = budgetMapper.findByYearMonth(condition);
        return budgets.stream().map(
                (budget) -> {
                    BudgetDTO dto = new BudgetDTO();
                    dto.setId(budget.getId());
                    dto.setBudgetName(budget.getBudgetName());
                    dto.setBudgetMoney(MoneyUtil.fen2Yuan(budget.getBudgetMoney()));
                    dto.setYearMonth(budget.getBelongYear() + "-" + budget.getBelongMonth());
                    List<TagInfo> tagInfos = tagInfoMapper.listTagInfoByBudgetId(budget.getId(), budget.getUserId());
                    dto.setTagInfos(new TagInfoToDtoListSupplier(tagInfos).get());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public boolean updateBudget(Budget budget) {
        return super.update(budget);
    }

    @Override
    public boolean deleteBudget(Budget budget) {
        return super.deleteById(budget.getId());
    }

}
