package com.tony.billing.request.liability;

import com.tony.billing.request.BaseRequest;

import java.sql.Date;

/**
 *
 * @author jiangwj20966 2018/3/5
 */
public class LiabilityAddRequest extends BaseRequest {

    private Integer type;
    private Date repaymentDay;
    private Long amount;
    private Integer installment;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
