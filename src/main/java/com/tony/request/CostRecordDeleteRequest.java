package com.tony.request;

/**
 * Author by TonyJiang on 2017/6/3.
 */
public class CostRecordDeleteRequest extends BaseRequest {
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
