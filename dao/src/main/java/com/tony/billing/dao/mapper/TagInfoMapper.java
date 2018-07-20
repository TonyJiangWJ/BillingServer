package com.tony.billing.dao.mapper;

import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author by TonyJiang on 2017/7/12.
 */
@Repository
public interface TagInfoMapper {
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
