package com.tony.request;

/**
 * Author by TonyJiang on 2017/6/10.
 */
public class CostRecordHideRequest extends BaseRequest {
    private String tradeNo;
    private String nowStatus;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(String nowStatus) {
        this.nowStatus = nowStatus;
    }
}
