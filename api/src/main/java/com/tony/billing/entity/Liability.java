package com.tony.billing.entity;

import com.tony.billing.entity.base.BaseVersionedEntity;

import java.util.Date;

/**
 * 分期负债
 */
public class Liability extends BaseVersionedEntity implements Comparable<Liability> {
    private Long userId;
    private Date repaymentDay;
    private String name;
    private Long type;
    private Long amount;
    private Integer status;
    private Integer installment;
    private Integer index;
    private Long paid;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
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
        if (this.repaymentDay.getTime() > o.getRepaymentDay().getTime()) {
            return 1;
        } else if (this.repaymentDay.getTime() == o.getRepaymentDay().getTime()) {
            return 0;
        }
        return -1;
    }
}
