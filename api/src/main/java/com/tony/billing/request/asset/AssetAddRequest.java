package com.tony.billing.request.asset;

import com.tony.billing.request.BaseRequest;

/**
 * @author TonyJiang on 2018/7/12
 */
public class AssetAddRequest extends BaseRequest {
    private Integer type;
    private String name;
    private Long amount;
    private Boolean available;

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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
