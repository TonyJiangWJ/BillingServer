package com.tony.billing.response.budget;

import com.tony.billing.dto.BudgetCostDto;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * Author jiangwj20966 on 2017/7/13.
 */
public class BudgetCostQueryResponse extends BaseResponse {
    private List<BudgetCostDto> budgetCostList;

    public List<BudgetCostDto> getBudgetCostList() {
        return budgetCostList;
    }

    public void setBudgetCostList(List<BudgetCostDto> budgetCostList) {
        this.budgetCostList = budgetCostList;
    }
}
