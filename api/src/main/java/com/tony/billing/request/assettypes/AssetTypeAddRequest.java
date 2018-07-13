package com.tony.billing.request.assettypes;

import com.tony.billing.request.BaseRequest;

/**
 * @author jiangwj20966 7/2/2018
 */
public class AssetTypeAddRequest extends BaseRequest {
    private String type;
    private String parentCode;
    private String typeIdentify;
    private String typeCode;
    private String typeDesc;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
