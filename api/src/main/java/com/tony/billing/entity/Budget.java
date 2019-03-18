package com.tony.billing.entity;

import com.tony.billing.annotation.Table;
import com.tony.billing.entity.base.BaseVersionedEntity;

/**
 * @author by TonyJiang on 2017/7/5.
 */
@Table("t_budget")
public class Budget extends BaseVersionedEntity {
    private Long userId;
    private String budgetName;
    private Long budgetMoney;
    private String belongYear;
    private Integer belongMonth;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBudgetName() {
        return budgetName;
    }

    public void setBudgetName(String budgetName) {
        this.budgetName = budgetName;
    }

    public Long getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(Long budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    public String getBelongYear() {
        return belongYear;
    }

    public void setBelongYear(String belongYear) {
        this.belongYear = belongYear;
    }

    public Integer getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(Integer belongMonth) {
        this.belongMonth = belongMonth;
    }
}
