package com.tony.entity.query;

/**
 * Author by TonyJiang on 2017/6/11.
 */
public class ReportEntityQuery {
    private String month;
    private Integer isHidden;
    private Integer isDeleted;
    private String inOutType;
    private Long userId;

    public ReportEntityQuery() {
    }

    public ReportEntityQuery(String month, Integer isHidden, Integer isDeleted, String inOutType, Long userId) {
        this.month = month;
        this.isHidden = isHidden;
        this.isDeleted = isDeleted;
        this.inOutType = inOutType;
        this.userId = userId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getInOutType() {
        return inOutType;
    }

    public void setInOutType(String inOutType) {
        this.inOutType = inOutType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
