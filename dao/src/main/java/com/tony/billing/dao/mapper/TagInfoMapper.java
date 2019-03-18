package com.tony.billing.dao.mapper;

import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.entity.TagBudgetRef;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author by TonyJiang on 2017/7/12.
 */
@Repository
public interface TagInfoMapper extends AbstractMapper<TagInfo> {

    List<TagInfo> listTagInfoByTradeNo(Map param);

    TagInfo getTagInfoById(Long id);

    Long insertTagCostRef(TagCostRef tagCostRef);

    Long deleteCostTag(@Param("costId") Long costId, @Param("tagId") Long tagId, @Param("modifyTime") Date modifyTime);

    Long deleteTagById(Map param);

    Long deleteCostTagByTagId(Map param);

    List<TagInfo> queryTagByName(@Param("tagName") String tagName, @Param("userId") Long userId);

    Long countTagUsage(Long id);

    Long insertTagBudgetRef(TagBudgetRef tagBudgetRef);

    /**
     * 删除预算和标签关联关系
     * @param tagId
     * @param budgetId
     * @param modifyTime
     * @return
     */
    Long deleteBudgetTag(@Param("tagId") Long tagId, @Param("budgetId") Long budgetId, @Param("modifyTime") Date modifyTime);

    /**
     * 获取预算关联的标签列表
     * @param budgetId
     * @param userId
     * @return
     */
    List<TagInfo> listTagInfoByBudgetId(@Param("budgetId") Long budgetId, @Param("userId") Long userId);
}
