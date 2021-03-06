package com.tony.billing.service;

import com.tony.billing.dto.TagInfoDTO;
import com.tony.billing.entity.TagBudgetRef;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;

import java.util.List;

/**
 * @author by TonyJiang on 2017/6/15.
 */
public interface TagInfoService {
    List<TagInfo> listTagInfo(TagInfo tagInfo);

    Long putTagInfo(TagInfo tagInfo);

    TagInfo findTagInfoByName(String tagName);

    List<TagInfo> listTagInfoByTradeNo(String tradeNo);

    TagInfo getTagInfoById(Long id);

    Long insertTagCostRef(TagCostRef tagCostRef);

    Long deleteTagById(Long id);

    Long countTagUsage(Long id);

    /**
     * 添加预算标签关联关系
     *
     * @param budgetRef
     * @return
     */
    Long insertTagBudgetRef(TagBudgetRef budgetRef);

    /**
     * 获取预算关联标签列表
     *
     * @param budgetId
     * @return
     */
    List<TagInfo> listTagInfoByBudgetId(Long budgetId);

    Long delBudgetTag(Long tagId, Long budgetId);

    List<TagInfo> listAssignableTagsByBudgetId(Long budgetId);

    boolean deleteCostTag(Long costId, Long tagId);

    /**
     * 批量新增账单标签关联关系
     *
     * @param tagId 标签id
     * @param recordIds 账单id列表
     * @return
     */
    boolean batchInsertTagCostRef(Long tagId, List<Long> recordIds);

    List<TagInfoDTO> listCommonTagInfos(List<Long> recordIds);

    boolean batchDeleteCostTags(Long tagId, List<Long> recordIds);
}
