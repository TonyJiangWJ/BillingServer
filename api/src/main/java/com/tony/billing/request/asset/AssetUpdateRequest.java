package com.tony.billing.request.asset;

import com.tony.billing.request.BaseRequest;

/**
 *
 * @author jiangwj20966 2018/2/22
 */
public class AssetUpdateRequest extends BaseRequest {
    private Long id;
    private Long amount;
    private String name;
    private String available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
