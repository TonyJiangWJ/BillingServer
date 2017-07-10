package com.tony.response;

import com.tony.model.CostRecordDetailModel;

/**
 * Author by TonyJiang on 2017/6/2.
 */
public class CostRecordDetailResponse extends BaseResponse {
    private CostRecordDetailModel recordDetail;

    public CostRecordDetailModel getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(CostRecordDetailModel recordDetail) {
        this.recordDetail = recordDetail;
    }
}
