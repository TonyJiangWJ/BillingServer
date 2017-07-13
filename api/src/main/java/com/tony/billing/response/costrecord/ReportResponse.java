package com.tony.billing.response.costrecord;

import com.tony.billing.dto.ReportDto;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/10.
 */
public class ReportResponse extends BaseResponse {
    private List<ReportDto> reportList;

    public List<ReportDto> getReportList() {
        return reportList;
    }

    public void setReportList(List<ReportDto> reportList) {
        this.reportList = reportList;
    }
}
