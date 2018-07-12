package com.tony.billing.request.asset;

import com.tony.billing.request.BaseRequest;

/**
 * @author TonyJiang on 2018/7/12
 */
public class AssetAddRequest extends BaseRequest {
    private Integer type;
    private String name;
    private Long amount;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
