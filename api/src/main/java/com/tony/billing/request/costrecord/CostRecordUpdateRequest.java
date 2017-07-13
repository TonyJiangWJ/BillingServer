package com.tony.billing.request.costrecord;

import com.tony.billing.request.BaseRequest;

/**
 * Author by TonyJiang on 2017/6/9.
 */
public class CostRecordUpdateRequest extends BaseRequest {
    private String tradeNo;
    private String goodsName;
    private String memo;
    private String location;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
