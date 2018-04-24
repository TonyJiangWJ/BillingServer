package com.tony.billing.dto;

/**
 * @author jiangwj20966 2018/4/24
 */
public class AssetTypeDTO {
    private String typeName;
    private String parentType;
    private String type;

    public AssetTypeDTO() {
    }

    public AssetTypeDTO(String typeName, String parentType, String type) {
        this.typeName = typeName;
        this.parentType = parentType;
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
