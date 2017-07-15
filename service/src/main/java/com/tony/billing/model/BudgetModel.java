package com.tony.billing.model;

import com.tony.billing.entity.Budget;

/**
 * Author by TonyJiang on 2017/7/13.
 */
public class BudgetModel extends Budget {
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
