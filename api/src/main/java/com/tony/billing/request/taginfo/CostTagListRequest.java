package com.tony.billing.request.taginfo;

import com.tony.billing.request.BaseRequest;

/**
 * @author by TonyJiang on 2017/6/15.
 */
public class CostTagListRequest extends BaseRequest {
    private String tradeNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
}
