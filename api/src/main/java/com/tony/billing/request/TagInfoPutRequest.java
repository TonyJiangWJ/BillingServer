package com.tony.billing.request;

/**
 * Author by TonyJiang on 2017/6/25.
 */
public class TagInfoPutRequest extends BaseRequest {
    private String tagName;

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
