package com.tony.billing.response.budget;

import com.tony.billing.dto.BudgetDto;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * Author jiangwj20966 on 2017/7/13.
 */
public class BudgetListResponse extends BaseResponse {
    private List<BudgetDto> budgetList;

    public List<BudgetDto> getBudgetList() {
        return budgetList;
    }

    public void setBudgetList(List<BudgetDto> budgetList) {
        this.budgetList = budgetList;
    }
}
