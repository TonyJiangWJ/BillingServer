package com.tony.billing.request.budget;

import com.tony.billing.request.BaseRequest;

/**
 * Author jiangwj20966 on 2017/7/13.
 */
public class BudgetListRequest extends BaseRequest {
    private String year;
    private Integer month;

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
