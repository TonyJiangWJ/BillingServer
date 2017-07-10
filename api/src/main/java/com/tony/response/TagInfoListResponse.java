package com.tony.response;

import com.tony.model.TagInfoModel;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/15.
 */
public class TagInfoListResponse extends BaseResponse {
    private List<TagInfoModel> tagInfoList;

    public List<TagInfoModel> getTagInfoList() {
        return tagInfoList;
    }

    public void setTagInfoList(List<TagInfoModel> tagInfoList) {
        this.tagInfoList = tagInfoList;
    }
}
