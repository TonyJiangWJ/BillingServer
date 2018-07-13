package com.tony.billing.entity;

import java.util.Date;

/**
 * @author TonyJiang 2018/6/21
 */
public class AssetTypes {
    private Integer id;
    private Long userId;
    private String parentCode;
    private String typeIdentify;
    private String typeCode;
    private String typeDesc;
    private Date createTime;
    private Date modifyTime;
    private Integer isDeleted;

    public boolean isParentType() {
        return this.parentCode == null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
