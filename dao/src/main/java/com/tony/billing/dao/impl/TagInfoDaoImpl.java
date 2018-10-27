package com.tony.billing.dao.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.dao.TagInfoDao;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.util.UserIdContainer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author by TonyJiang on 2017/7/12.
 */
@Service("tagInfoDao")
public class TagInfoDaoImpl implements TagInfoDao {

    @Resource
    private TagInfoMapper tagInfoMapper;

    @Override
    public Long insert(TagInfo tagInfo) {
        return tagInfoMapper.insert(tagInfo);
    }

    @Override
    public Long updateById(TagInfo tagInfo) {
        return tagInfoMapper.updateById(tagInfo);
    }

    @Override
    public List<TagInfo> find(TagInfo tagInfo) {
        return tagInfoMapper.find(tagInfo);
    }

    @Override
    public List<TagInfo> listTagInfoByTradeNo(String tradeNo) {
        Map<String, Object> params = new HashMap<>(2);
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
        return tagInfoMapper.insertTagCostRef(tagCostRef);
    }

    @Override
    public Long deleteCostTag(Map param) {
        return tagInfoMapper.deleteCostTag(param);
    }

    @Override
    public Long deleteTagById(Map param) {
        return tagInfoMapper.deleteTagById(param);
    }

    @Override
    public Long deleteCostTagByTagId(Map param) {
        return tagInfoMapper.deleteCostTagByTagId(param);
    }

    @Override
    public List<TagInfo> queryTagByName(String tagName) {
        return tagInfoMapper.queryTagByName(tagName);
    }
}
