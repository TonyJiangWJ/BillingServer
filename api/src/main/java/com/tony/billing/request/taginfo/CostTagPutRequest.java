package com.tony.billing.request.taginfo;

import com.tony.billing.request.BaseRequest;

/**
 * @author by TonyJiang on 2017/6/25.
 */
public class CostTagPutRequest extends BaseRequest {
    private String tradeNo;
    private Long tagId;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
