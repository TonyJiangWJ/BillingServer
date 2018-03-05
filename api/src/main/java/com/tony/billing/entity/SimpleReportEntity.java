package com.tony.billing.entity;

/**
 * Author by TonyJiang on 2017/6/11.
 */
public class SimpleReportEntity {
    private String month;
    private Long totalCost;
    private Long totalIncome;

    public SimpleReportEntity() {
    }

    public SimpleReportEntity(String month, Long totalCost, Long totalIncome) {
        this.month = month;
        this.totalCost = totalCost;
        this.totalIncome = totalIncome;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }
}
