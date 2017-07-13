package com.tony.billing.response.costrecord;

import com.tony.billing.dto.CostRecordDetailDto;
import com.tony.billing.response.BaseResponse;

/**
 * Author by TonyJiang on 2017/6/2.
 */
public class CostRecordDetailResponse extends BaseResponse {
    private CostRecordDetailDto recordDetail;

    public CostRecordDetailDto getRecordDetail() {
        return recordDetail;
    }

    public void setRecordDetail(CostRecordDetailDto recordDetail) {
        this.recordDetail = recordDetail;
    }
}
