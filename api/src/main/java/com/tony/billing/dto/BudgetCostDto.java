package com.tony.billing.dto;

/**
 * Author by TonyJiang on 2017/7/12.
 */
public class BudgetCostDto {
    private String tagName;
    private Long totalCost;
    private Long budgetMoney;
    private String month;
    private String year;
    private Long restMoney;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(Long budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Long getRestMoney() {
        return restMoney;
    }

    public void setRestMoney(Long restMoney) {
        this.restMoney = restMoney;
    }
}
