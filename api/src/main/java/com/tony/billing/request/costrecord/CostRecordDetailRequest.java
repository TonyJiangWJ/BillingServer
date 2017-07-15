package com.tony.billing.request.costrecord;

import com.tony.billing.request.BaseRequest;

/**
 * Author by TonyJiang on 2017/6/2.
 */
public class CostRecordDetailRequest extends BaseRequest {
    private String tradeNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
