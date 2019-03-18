package com.tony.billing.entity;

import com.tony.billing.entity.base.BaseEntity;

import java.util.Date;

/**
 * @author jiangwenjie 2019-03-13
 */
public class TagBudgetRef extends BaseEntity {
    private Long budgetId;
    private Long tagId;

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
