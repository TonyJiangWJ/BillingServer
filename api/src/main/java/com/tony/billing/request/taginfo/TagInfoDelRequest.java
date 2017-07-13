package com.tony.billing.request.taginfo;

import com.tony.billing.request.BaseRequest;

/**
 * Author by TonyJiang on 2017/6/28.
 */
public class TagInfoDelRequest extends BaseRequest {
    private Long tagId;

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }
}
