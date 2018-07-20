package com.tony.billing.model;

import com.tony.billing.entity.Budget;

/**
 * @author by TonyJiang on 2017/7/12.
 */
public class BudgetCostModel extends Budget {
    private String tagName;
    private Long totalCost;
    private Long restMoney;
    private Integer isDeleted;
    private Integer isHidden;
    private Long costExpHidden;
    private Long costExpDelete;
    private Long costClear;

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

    public Long getRestMoney() {
        return restMoney;
    }

    public void setRestMoney(Long restMoney) {
        this.restMoney = restMoney;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    public Long getCostExpHidden() {
        return costExpHidden;
    }

    public void setCostExpHidden(Long costExpHidden) {
        this.costExpHidden = costExpHidden;
    }

    public Long getCostExpDelete() {
        return costExpDelete;
    }

    public void setCostExpDelete(Long costExpDelete) {
        this.costExpDelete = costExpDelete;
    }

    public Long getCostClear() {
        return costClear;
    }

    public void setCostClear(Long costClear) {
        this.costClear = costClear;
    }
}
