package com.tony.billing.response.taginfo;

import com.tony.billing.dto.TagInfoDto;
import com.tony.billing.response.BaseResponse;

import java.util.List;

/**
 * Author by TonyJiang on 2017/6/15.
 */
public class CostTagListResponse extends BaseResponse {
    private List<TagInfoDto> tagInfoModels;

    public List<TagInfoDto> getTagInfoModels() {
        return tagInfoModels;
    }

    public void setTagInfoModels(List<TagInfoDto> tagInfoDtos) {
        this.tagInfoModels = tagInfoDtos;
    }
}
