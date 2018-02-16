package com.tony.billing.entity;

import java.util.Date;

/**
 * 分期负债
 */
public class Liability implements Comparable<Liability> {

    private Long id;
    private Long userId;
    private Date createTime;
    private Date repaymentDay;
    private Date modifyTime;
    private String name;
    private String type;
    private String parentType;
    private Long amount;
    private Integer status;
    private Integer installment;
    private Integer index;
    private Long paid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(Date repaymentDay) {
        this.repaymentDay = repaymentDay;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getInstallment() {
        return installment;
    }

    public void setInstallment(Integer installment) {
        this.installment = installment;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getPaid() {
        return paid;
    }

    public void setPaid(Long paid) {
        this.paid = paid;
    }

    @Override
    public int compareTo(Liability o) {
        if (o == null) {
            return 1;
        }
        Liability target = (Liability) o;
        if (this.repaymentDay.getTime() > target.getRepaymentDay().getTime()) {
            return 1;
        } else if (this.repaymentDay.getTime() == target.getRepaymentDay().getTime()) {
            return 0;
        }
        return -1;
    }
}
