package com.tony.billing.response.taginfo;

import com.tony.billing.dto.TagInfoDto;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/15.
 */
public class TagInfoListResponse extends BaseResponse {
    private List<TagInfoDto> tagInfoList;

    public List<TagInfoDto> getTagInfoList() {
        return tagInfoList;
    }

    public void setTagInfoList(List<TagInfoDto> tagInfoList) {
        this.tagInfoList = tagInfoList;
    }
}
