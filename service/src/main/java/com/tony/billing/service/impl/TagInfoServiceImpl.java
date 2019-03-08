package com.tony.billing.service.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.constants.enums.EnumDeleted;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.service.TagInfoService;
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
public class TagInfoServiceImpl implements TagInfoService {
    @Resource
    private TagInfoMapper tagInfoMapper;

    @Override
    public List<TagInfo> listTagInfo(TagInfo tagInfo) {
        return tagInfoMapper.find(tagInfo);
    }

    @Override
    public Long putTagInfo(TagInfo tagInfo) {
        if (CollectionUtils.isNotEmpty(tagInfoMapper.find(tagInfo))) {
            return -1L;
        }
        tagInfo.setCreateTime(new Date());
        tagInfo.setModifyTime(new Date());
        tagInfo.setIsDelete(EnumDeleted.NOT_DELETED.val());
        if (tagInfoMapper.insert(tagInfo) > 0) {
            return tagInfo.getId();
        }
        return -1L;
    }

    @Override
    public TagInfo findTagInfoByName(String tagName) {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setTagName(tagName);
        tagInfo.setUserId(UserIdContainer.getUserId());
        List<TagInfo> tagInfos = tagInfoMapper.find(tagInfo);
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
        tagCostRef.setIsDelete(EnumDeleted.NOT_DELETED.val());
        if (tagInfoMapper.insertTagCostRef(tagCostRef) > 0) {
            return tagCostRef.getId();
        }
        return -1L;
    }

    @Override
    public Long deleteCostTag(Map param) {
        if (param.get("costId") == null || param.get("tagId") == null) {
            throw new RuntimeException("param error");
        }
        param.put("modifyTime", new Date());
        return tagInfoMapper.deleteCostTag(param);
    }

    @Override
    public Long deleteTagById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("tagId", id);
        param.put("modifyTime", new Date());
        tagInfoMapper.deleteCostTagByTagId(param);
        return tagInfoMapper.deleteTagById(param);
    }

}
