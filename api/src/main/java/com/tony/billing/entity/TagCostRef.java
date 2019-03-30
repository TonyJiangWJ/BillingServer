package com.tony.billing.entity;

import com.tony.billing.annotation.Table;
import com.tony.billing.entity.base.BaseEntity;

import java.util.Date;

/**
 * @author by TonyJiang on 2017/6/15.
 */
@Table("t_cost_tag")
public class TagCostRef extends BaseEntity {
    private Long tagId;
    private Long costId;


    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public Long getCostId() {
        return costId;
    }

    public void setCostId(Long costId) {
        this.costId = costId;
    }

}
