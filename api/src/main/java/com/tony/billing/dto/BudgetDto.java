package com.tony.billing.dto;

/**
 * Author jiangwj20966 on 2017/7/13.
 */
public class BudgetDto {
    private String tagName;
    private String budgetMoney;
    private String yearMonth;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(String budgetMoney) {
        this.budgetMoney = budgetMoney;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }
}
