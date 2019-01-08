package com.tony.billing.entity;

/**
 * @author jiangwj20966 8/6/2018
 */
public class RawReportEntity {
    private Long totalCost;
    private Long totalCostExceptDeleted;
    private Long totalCostExceptHidden;
    private Long totalCostExceptDeletedAndHidden;
    private Long totalCostHidden;
    private Long totalCostDeleted;
    private Long totalCostDeletedAndHidden;
    private Long totalIncome;
    private Long totalIncomeExceptDeleted;
    private Long totalIncomeExceptHidden;
    private Long totalIncomeExceptDeletedAndHidden;
    private Long totalIncomeHidden;
    private Long totalIncomeDeleted;
    private Long totalIncomeDeletedAndHidden;

    public void calculateAdditional() {
        totalCostExceptDeleted = totalCost - totalCostDeleted;
        totalCostExceptHidden = totalCost - totalCostHidden;


        totalIncomeExceptDeleted = totalIncome - totalIncomeDeleted;
        totalIncomeExceptHidden = totalIncome - totalIncomeHidden;

    }

    public Long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Long totalCost) {
        this.totalCost = totalCost;
    }

    public Long getTotalCostExceptDeleted() {
        return totalCostExceptDeleted;
    }

    public void setTotalCostExceptDeleted(Long totalCostExceptDeleted) {
        this.totalCostExceptDeleted = totalCostExceptDeleted;
    }

    public Long getTotalCostExceptHidden() {
        return totalCostExceptHidden;
    }

    public void setTotalCostExceptHidden(Long totalCostExceptHidden) {
        this.totalCostExceptHidden = totalCostExceptHidden;
    }

    public Long getTotalCostExceptDeletedAndHidden() {
        return totalCostExceptDeletedAndHidden;
    }

    public void setTotalCostExceptDeletedAndHidden(Long totalCostExceptDeletedAndHidden) {
        this.totalCostExceptDeletedAndHidden = totalCostExceptDeletedAndHidden;
    }

    public Long getTotalCostHidden() {
        return totalCostHidden;
    }

    public void setTotalCostHidden(Long totalCostHidden) {
        this.totalCostHidden = totalCostHidden;
    }

    public Long getTotalCostDeleted() {
        return totalCostDeleted;
    }

    public void setTotalCostDeleted(Long totalCostDeleted) {
        this.totalCostDeleted = totalCostDeleted;
    }

    public Long getTotalCostDeletedAndHidden() {
        return totalCostDeletedAndHidden;
    }

    public void setTotalCostDeletedAndHidden(Long totalCostDeletedAndHidden) {
        this.totalCostDeletedAndHidden = totalCostDeletedAndHidden;
    }

    public Long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Long getTotalIncomeExceptDeleted() {
        return totalIncomeExceptDeleted;
    }

    public void setTotalIncomeExceptDeleted(Long totalIncomeExceptDeleted) {
        this.totalIncomeExceptDeleted = totalIncomeExceptDeleted;
    }

    public Long getTotalIncomeExceptHidden() {
        return totalIncomeExceptHidden;
    }

    public void setTotalIncomeExceptHidden(Long totalIncomeExceptHidden) {
        this.totalIncomeExceptHidden = totalIncomeExceptHidden;
    }

    public Long getTotalIncomeExceptDeletedAndHidden() {
        return totalIncomeExceptDeletedAndHidden;
    }

    public void setTotalIncomeExceptDeletedAndHidden(Long totalIncomeExceptDeletedAndHidden) {
        this.totalIncomeExceptDeletedAndHidden = totalIncomeExceptDeletedAndHidden;
    }

    public Long getTotalIncomeHidden() {
        return totalIncomeHidden;
    }

    public void setTotalIncomeHidden(Long totalIncomeHidden) {
        this.totalIncomeHidden = totalIncomeHidden;
    }

    public Long getTotalIncomeDeleted() {
        return totalIncomeDeleted;
    }

    public void setTotalIncomeDeleted(Long totalIncomeDeleted) {
        this.totalIncomeDeleted = totalIncomeDeleted;
    }

    public Long getTotalIncomeDeletedAndHidden() {
        return totalIncomeDeletedAndHidden;
    }

    public void setTotalIncomeDeletedAndHidden(Long totalIncomeDeletedAndHidden) {
        this.totalIncomeDeletedAndHidden = totalIncomeDeletedAndHidden;
    }
}
