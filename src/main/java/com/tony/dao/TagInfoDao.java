package com.tony.dao;

import com.tony.entity.TagCostRef;
import com.tony.entity.TagInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/6/14.
 */
@Repository
public interface TagInfoDao {
    Long insert(TagInfo tagInfo);

    Long updateById(TagInfo tagInfo);

    List<TagInfo> find(TagInfo tagInfo);

    List<TagInfo> listTagInfoByTradeNo(String tradeNo);

    TagInfo getTagInfoById(Long id);

    Long insertTagCostRef(TagCostRef tagCostRef);

    Long deleteCostTag(Map param);

    Long deleteTagById(Map param);

    Long deleteCostTagByTagId(Map param);

    List<TagInfo> queryTagByName(String tagName);
}
