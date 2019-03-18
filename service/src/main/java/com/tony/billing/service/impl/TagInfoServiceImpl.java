package com.tony.billing.service.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.constants.enums.EnumDeleted;
import com.tony.billing.dao.mapper.BudgetMapper;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.TagBudgetRef;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.service.TagInfoService;
import com.tony.billing.service.base.AbstractService;
import com.tony.billing.util.UserIdContainer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by TonyJiang on 2017/6/15.
 */
@Service
public class TagInfoServiceImpl extends AbstractService<TagInfo> implements TagInfoService {
    @Resource
    private TagInfoMapper tagInfoMapper;
    @Resource
    private BudgetMapper budgetMapper;


    @Override
    protected AbstractMapper<TagInfo> getMapper() {
        return tagInfoMapper;
    }

    @Override
    public List<TagInfo> listTagInfo(TagInfo tagInfo) {
        return super.list(tagInfo);
    }

    @Override
    public Long putTagInfo(TagInfo tagInfo) {
        if (CollectionUtils.isNotEmpty(tagInfoMapper.list(tagInfo))) {
            logger.error("同名标签已存在:{}", tagInfo.getTagName());
            return -1L;
        }
        return super.insert(tagInfo);
    }

    @Override
    public TagInfo findTagInfoByName(String tagName) {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setTagName(tagName);
        tagInfo.setUserId(UserIdContainer.getUserId());
        List<TagInfo> tagInfos = tagInfoMapper.list(tagInfo);
        if (!CollectionUtils.isEmpty(tagInfos)) {
            return tagInfos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<TagInfo> listTagInfoByTradeNo(String tradeNo) {
        Map<String, Object> params = new HashMap<>(4);
        params.put("tradeNo", tradeNo);
        params.put("userId", UserIdContainer.getUserId());
        Preconditions.checkNotNull(params.get("userId"));
        Preconditions.checkState(StringUtils.isNotEmpty(tradeNo));
        return tagInfoMapper.listTagInfoByTradeNo(params);
    }

    @Override
    public TagInfo getTagInfoById(Long id) {
        return tagInfoMapper.getTagInfoById(id);
    }

    @Override
    public Long insertTagCostRef(TagCostRef tagCostRef) {
        tagCostRef.setCreateTime(new Date());
        tagCostRef.setModifyTime(new Date());
        tagCostRef.setIsDeleted(EnumDeleted.NOT_DELETED.val());
        if (tagInfoMapper.insertTagCostRef(tagCostRef) > 0) {
            return tagCostRef.getId();
        }
        return -1L;
    }

    @Override
    public boolean deleteCostTag(Long costId, Long tagId) {
        return tagInfoMapper.deleteCostTag(costId, tagId, new Date()) > 0;
    }

    @Override
    public Long deleteTagById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("tagId", id);
        param.put("modifyTime", new Date());
        tagInfoMapper.deleteCostTagByTagId(param);
        return tagInfoMapper.deleteTagById(param);
    }

    @Override
    public Long countTagUsage(Long id) {
        return tagInfoMapper.countTagUsage(id);
    }

    @Override
    public Long insertTagBudgetRef(TagBudgetRef budgetRef) {
        Preconditions.checkNotNull(budgetRef.getBudgetId());
        Preconditions.checkNotNull(budgetRef.getTagId());
        budgetRef.setCreateTime(new Date());
        budgetRef.setModifyTime(new Date());
        budgetRef.setIsDeleted(EnumDeleted.NOT_DELETED.val());
        TagInfo tagInfo = tagInfoMapper.getTagInfoById(budgetRef.getTagId());
        Budget budget = budgetMapper.getById(budgetRef.getBudgetId(), UserIdContainer.getUserId());
        if (tagInfo != null && budget != null
                && tagInfo.getUserId().equals(UserIdContainer.getUserId())) {
            return tagInfoMapper.insertTagBudgetRef(budgetRef);
        }
        return -1L;
    }

    @Override
    public List<TagInfo> listTagInfoByBudgetId(Long budgetId) {
        return tagInfoMapper.listTagInfoByBudgetId(budgetId, UserIdContainer.getUserId());
    }

}
