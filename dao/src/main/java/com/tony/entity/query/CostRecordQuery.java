package com.tony.entity.query;

import com.tony.annotation.Table;
import com.tony.entity.CostRecord;

/**
 * Author by TonyJiang on 2017/6/9.
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
