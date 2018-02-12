package com.tony.billing.model;

import com.tony.billing.dto.AssetDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TonyJiang on 2018/2/12
 */
public class AssetModel {
    private String name;
    private String type;
    private Long total;
    private List<AssetDTO> assetList;

    public AssetModel() {
        assetList = new ArrayList<>();
        total = 0L;
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<AssetDTO> getAssetList() {
        return assetList;
    }

    public void setAssetList(List<AssetDTO> assetList) {
        this.assetList = assetList;
    }
}
