package com.tony.billing.entity.query;

/**
 * @author by TonyJiang on 2017/6/11.
 */
public class ReportEntityQuery {
    private String datePrefix;
    private Long userId;

    public String getDatePrefix() {
        return datePrefix;
    }

    public void setDatePrefix(String datePrefix) {
        this.datePrefix = datePrefix;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
