package com.tony.billing.entity.query;

import com.tony.billing.annotation.Table;
import com.tony.billing.entity.CostRecord;

/**
 * @author by TonyJiang on 2017/6/9.
 */
@Table("t_sjf_sb")
public class CostRecordQuery extends CostRecord {
    private String startDate;
    private String endDate;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
