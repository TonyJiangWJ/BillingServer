package com.tony.billing.entity;

import com.tony.billing.annotation.Table;
import com.tony.billing.entity.base.BaseVersionedEntity;

/**
 * @author jiangwj20966 on 2017/6/2.
 */
@Table("t_cost_info")
public class CostRecord extends BaseVersionedEntity {
    private String tradeNo;
    private String orderNo;
    private String costCreateTime;
    private String paidTime;
    private String costModifyTime;
    private String location;
    private String orderType;
    private String target;
    private String goodsName;
    private Long money;
    private String inOutType;
    private String orderStatus;
    private Long serviceCost;
    private Long refundMoney;
    private String memo;
    private String tradeStatus;
    private Integer isHidden;
    private Long userId;

    public String getCostCreateTime() {
        return costCreateTime;
    }

    public void setCostCreateTime(String costCreateTime) {
        this.costCreateTime = costCreateTime;
    }

    public String getCostModifyTime() {
        return costModifyTime;
    }

    public void setCostModifyTime(String costModifyTime) {
        this.costModifyTime = costModifyTime;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPaidTime() {
        return paidTime;
    }

    public void setPaidTime(String paidTime) {
        this.paidTime = paidTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(Long serviceCost) {
        this.serviceCost = serviceCost;
    }

    public Long getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Long refundMoney) {
        this.refundMoney = refundMoney;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
