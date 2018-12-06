package com.tony.billing.entity;

/**
 * @author by TonyJiang on 2017/6/11.
 */
public class ReportEntity {
    private String month;
    private String totalCost;
    private String totalCostExceptDeleted;
    private String totalCostExceptHidden;
    private String totalCostExceptDeletedAndHidden;
    private String totalCostHidden;
    private String totalCostDeleted;
    private String totalCostDeletedAndHidden;
    private String totalIncome;
    private String totalIncomeExceptDeleted;
    private String totalIncomeExceptHidden;
    private String totalIncomeExceptDeletedAndHidden;
    private String totalIncomeHidden;
    private String totalIncomeDeleted;
    private String totalIncomeDeletedAndHidden;

    public ReportEntity(String datePrefix, RawReportEntity rawReportEntity) {
        month = datePrefix;
        totalCost = fen2Yuan(rawReportEntity.getTotalCost());
        totalCostExceptDeleted = fen2Yuan(rawReportEntity.getTotalCostExceptDeleted());
        totalCostExceptHidden = fen2Yuan(rawReportEntity.getTotalCostExceptHidden());
        totalCostExceptDeletedAndHidden = fen2Yuan(rawReportEntity.getTotalCostExceptDeletedAndHidden());
        totalCostHidden = fen2Yuan(rawReportEntity.getTotalCostHidden());
        totalCostDeleted = fen2Yuan(rawReportEntity.getTotalCostDeleted());
        totalCostDeletedAndHidden = fen2Yuan(rawReportEntity.getTotalCostDeletedAndHidden());
        totalIncome = fen2Yuan(rawReportEntity.getTotalIncome());
        totalIncomeExceptDeleted = fen2Yuan(rawReportEntity.getTotalIncomeExceptDeleted());
        totalIncomeExceptHidden = fen2Yuan(rawReportEntity.getTotalIncomeExceptHidden());
        totalIncomeExceptDeletedAndHidden = fen2Yuan(rawReportEntity.getTotalIncomeExceptDeletedAndHidden());
        totalIncomeHidden = fen2Yuan(rawReportEntity.getTotalIncomeHidden());
        totalIncomeDeleted = fen2Yuan(rawReportEntity.getTotalIncomeDeleted());
        totalIncomeDeletedAndHidden = fen2Yuan(rawReportEntity.getTotalIncomeDeletedAndHidden());
    }

    private String fen2Yuan(Long money) {
        if (money == null) {
            return null;
        }
        String s = money + "";
        if (s.length() < 2) {
            s = "0.0" + s;
        } else if (s.length() == 2) {
            s = "0." + s;
        } else {
            s = s.substring(0, s.length() - 2) + "." + s.substring(s.length() - 2);
        }
        return s;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(String totalIncome) {
        this.totalIncome = totalIncome;
    }

    public String getTotalCostExceptDeleted() {
        return totalCostExceptDeleted;
    }

    public void setTotalCostExceptDeleted(String totalCostExceptDeleted) {
        this.totalCostExceptDeleted = totalCostExceptDeleted;
    }

    public String getTotalCostExceptHidden() {
        return totalCostExceptHidden;
    }

    public void setTotalCostExceptHidden(String totalCostExceptHidden) {
        this.totalCostExceptHidden = totalCostExceptHidden;
    }

    public String getTotalIncomeExceptDeleted() {
        return totalIncomeExceptDeleted;
    }

    public void setTotalIncomeExceptDeleted(String totalIncomeExceptDeleted) {
        this.totalIncomeExceptDeleted = totalIncomeExceptDeleted;
    }

    public String getTotalIncomeExceptHidden() {
        return totalIncomeExceptHidden;
    }

    public void setTotalIncomeExceptHidden(String totalIncomeExceptHidden) {
        this.totalIncomeExceptHidden = totalIncomeExceptHidden;
    }

    public String getTotalCostExceptDeletedAndHidden() {
        return totalCostExceptDeletedAndHidden;
    }

    public void setTotalCostExceptDeletedAndHidden(String totalCostExceptDeletedAndHidden) {
        this.totalCostExceptDeletedAndHidden = totalCostExceptDeletedAndHidden;
    }

    public String getTotalCostHidden() {
        return totalCostHidden;
    }

    public void setTotalCostHidden(String totalCostHidden) {
        this.totalCostHidden = totalCostHidden;
    }

    public String getTotalCostDeleted() {
        return totalCostDeleted;
    }

    public void setTotalCostDeleted(String totalCostDeleted) {
        this.totalCostDeleted = totalCostDeleted;
    }

    public String getTotalCostDeletedAndHidden() {
        return totalCostDeletedAndHidden;
    }

    public void setTotalCostDeletedAndHidden(String totalCostDeletedAndHidden) {
        this.totalCostDeletedAndHidden = totalCostDeletedAndHidden;
    }

    public String getTotalIncomeExceptDeletedAndHidden() {
        return totalIncomeExceptDeletedAndHidden;
    }

    public void setTotalIncomeExceptDeletedAndHidden(String totalIncomeExceptDeletedAndHidden) {
        this.totalIncomeExceptDeletedAndHidden = totalIncomeExceptDeletedAndHidden;
    }

    public String getTotalIncomeHidden() {
        return totalIncomeHidden;
    }

    public void setTotalIncomeHidden(String totalIncomeHidden) {
        this.totalIncomeHidden = totalIncomeHidden;
    }

    public String getTotalIncomeDeleted() {
        return totalIncomeDeleted;
    }

    public void setTotalIncomeDeleted(String totalIncomeDeleted) {
        this.totalIncomeDeleted = totalIncomeDeleted;
    }

    public String getTotalIncomeDeletedAndHidden() {
        return totalIncomeDeletedAndHidden;
    }

    public void setTotalIncomeDeletedAndHidden(String totalIncomeDeletedAndHidden) {
        this.totalIncomeDeletedAndHidden = totalIncomeDeletedAndHidden;
    }
}
