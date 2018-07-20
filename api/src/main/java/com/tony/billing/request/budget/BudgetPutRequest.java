package com.tony.billing.request.budget;

import com.tony.billing.request.BaseRequest;

/**
 * @author jiangwj20966 on 2017/7/13.
 */
public class BudgetPutRequest extends BaseRequest {

    private Long tagId;
    private Long budgetMoney;
    private String year;
    private Integer month;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(Long budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
