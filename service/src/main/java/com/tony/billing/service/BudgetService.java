package com.tony.billing.service;

import com.tony.billing.dto.BudgetDTO;
import com.tony.billing.entity.Budget;

import java.util.List;

/**
 * @author by TonyJiang on 2017/7/5.
 */
public interface BudgetService {
    /**
     * 创建预算信息
     * @param budget
     * @return
     */
    Long insert(Budget budget);

    /**
     * 根据年月查询预算信息
     * @param budget belongYear belongMonth
     * @return
     */
    List<BudgetDTO> queryBudgetsByCondition(Budget budget);

    /**
     * 更新预算信息
     * @param budget
     * @return
     */
    boolean updateBudget(Budget budget);

    /**
     * 删除预算信息
     * @param budget
     * @return
     */
    boolean deleteBudget(Budget budget);

    Budget getById(Long id);


}
