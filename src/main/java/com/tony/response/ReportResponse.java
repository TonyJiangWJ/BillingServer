package com.tony.response;

import com.tony.model.ReportModel;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/10.
 */
public class ReportResponse extends BaseResponse {
    private List<ReportModel> reportList;

    public List<ReportModel> getReportList() {
        return reportList;
    }

    public void setReportList(List<ReportModel> reportList) {
        this.reportList = reportList;
    }
}
