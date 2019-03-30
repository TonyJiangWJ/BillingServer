package com.tony.billing.entity;


import com.tony.billing.annotation.Table;
import com.tony.billing.entity.base.BaseEntity;

/**
 * @author by TonyJiang on 2017/6/13.
 */
@Table("t_tag_info")
public class TagInfo extends BaseEntity {

    private String tagName;
    private Long userId;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
