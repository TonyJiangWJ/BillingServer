package com.tony.billing.entity;

import com.tony.billing.annotation.Table;

import java.util.Date;

/**
 * @author by TonyJiang on 2017/7/5.
 */
@Table("t_budget")
public class Budget {
    private Long userId;
    private Long id;
    private Long tagId;
    private Long budgetMoney;
    private Date createTime;
    private Date modifyTime;
    private String belongYear;
    private Integer belongMonth;
    private Integer isDelete;
    private Integer version;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getBudgetMoney() {
        return budgetMoney;
    }

    public void setBudgetMoney(Long budgetMoney) {
        this.budgetMoney = budgetMoney;
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

    public String getBelongYear() {
        return belongYear;
    }

    public void setBelongYear(String belongYear) {
        this.belongYear = belongYear;
    }

    public Integer getBelongMonth() {
        return belongMonth;
    }

    public void setBelongMonth(Integer belongMonth) {
        this.belongMonth = belongMonth;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
