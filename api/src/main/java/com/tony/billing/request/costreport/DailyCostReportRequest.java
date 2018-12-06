package com.tony.billing.request.costreport;

import com.tony.billing.request.BaseRequest;

/**
 * @author jiangwj20966 8/6/2018
 */
public class DailyCostReportRequest extends BaseRequest {
    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
