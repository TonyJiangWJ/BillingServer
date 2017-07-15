package com.tony.billing.dao;

import com.tony.billing.entity.TagInfo;
import com.tony.billing.entity.TagCostRef;

import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/6/14.
 */
public interface TagInfoDao {
    Long insert(TagInfo tagInfo);

    Long updateById(TagInfo tagInfo);

    List<TagInfo> find(TagInfo tagInfo);

    List<TagInfo> listTagInfoByTradeNo(Map param);

    TagInfo getTagInfoById(Long id);

    Long insertTagCostRef(TagCostRef tagCostRef);

    Long deleteCostTag(Map param);

    Long deleteTagById(Map param);

    Long deleteCostTagByTagId(Map param);

    List<TagInfo> queryTagByName(String tagName);
}
