package com.tony.billing.request.costrecord;

import com.tony.billing.request.BaseRequest;

/**
 * @author by TonyJiang on 2017/6/10.
 */
public class CostRecordHideRequest extends BaseRequest {
    private String tradeNo;
    private Integer nowStatus;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(Integer nowStatus) {
        this.nowStatus = nowStatus;
    }
}
