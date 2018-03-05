package com.tony.billing.dto;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/3/5
 */
public class LiabilityTypeDTO {
    private String typeName;
    private String type;
    private String parentType;

    public LiabilityTypeDTO() {
    }

    public LiabilityTypeDTO(String typeName, String type, String parentType) {
        this.typeName = typeName;
        this.type = type;
        this.parentType = parentType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
}
