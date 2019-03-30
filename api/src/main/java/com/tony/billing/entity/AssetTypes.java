package com.tony.billing.entity;

import com.tony.billing.entity.base.BaseVersionedEntity;

import java.util.Date;

/**
 * @author TonyJiang 2018/6/21
 */
public class AssetTypes extends BaseVersionedEntity {
    private Long userId;
    private String parentCode;
    private String typeIdentify;
    private String typeCode;
    private String typeDesc;

    public boolean isParentType() {
        return this.parentCode == null;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getTypeIdentify() {
        return typeIdentify;
    }

    public void setTypeIdentify(String typeIdentify) {
        this.typeIdentify = typeIdentify;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getTypeDesc() {
        return typeDesc;
    }

    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
