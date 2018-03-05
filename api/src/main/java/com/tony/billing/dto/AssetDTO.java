package com.tony.billing.dto;

import com.tony.billing.entity.Asset;

/**
 * @author TonyJiang on 2018/2/12
 */
public class AssetDTO {
    private String name;
    private String type;
    private String parentType;
    private Long id;
    private Long amount;

    public AssetDTO() {

    }

    public AssetDTO(Asset asset) {
        if (asset != null) {
            this.amount = asset.getAmount();
            this.id = asset.getId();
            this.name = asset.getName();
            this.type = asset.getType();
            this.parentType = asset.getParentType();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

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
