package com.tony.billing.request.liability;

import com.tony.billing.request.BaseRequest;

import java.sql.Date;

/**
 *
 * @author jiangwj20966 2018/3/5
 */
public class LiabilityAddRequest extends BaseRequest {

    private String type;
    private String parentType;
    private Date repaymentDay;
    private Long amount;
    private Integer installment;

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

    public Date getRepaymentDay() {
        return repaymentDay;
    }

    public void setRepaymentDay(Date repaymentDay) {
        this.repaymentDay = repaymentDay;
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
}
