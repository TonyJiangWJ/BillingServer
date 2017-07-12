package com.tony.billing.service;

import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;

import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/6/15.
 */
public interface TagInfoService {
    List<TagInfo> listTagInfo(TagInfo tagInfo);

    Long putTagInfo(TagInfo tagInfo);

    TagInfo findTagInfoByName(String tagName);

    List<TagInfo> listTagInfoByTradeNo(Map param);

    TagInfo getTagInfoById(Long id);

    Long insertTagCostRef(TagCostRef tagCostRef);

    Long deleteCostTag(Map param);

    Long deleteTagById(Long id);
}
