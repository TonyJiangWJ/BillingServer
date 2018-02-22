package com.tony.billing.request.asset;

import com.tony.billing.request.BaseRequest;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/2/22
 */
public class AssetUpdateRequest extends BaseRequest {
    private Long id;
    private Long amount;

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
}
