package com.tony.billing.response;

import com.tony.billing.model.TagInfoModel;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/15.
 */
public class CostTagListResponse extends BaseResponse {
    private List<TagInfoModel> tagInfoModels;

    public List<TagInfoModel> getTagInfoModels() {
        return tagInfoModels;
    }

    public void setTagInfoModels(List<TagInfoModel> tagInfoModels) {
        this.tagInfoModels = tagInfoModels;
    }
}
