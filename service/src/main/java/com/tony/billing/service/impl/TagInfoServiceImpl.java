package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumDeleted;
import com.tony.billing.dao.TagInfoDao;
import com.tony.billing.entity.TagCostRef;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.service.TagInfoService;
import org.apache.commons.collections.CollectionUtils;
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
    private TagInfoDao tagInfoDao;

    @Override
    public List<TagInfo> listTagInfo(TagInfo tagInfo) {
        return tagInfoDao.find(tagInfo);
    }

    @Override
    public Long putTagInfo(TagInfo tagInfo) {
        if (CollectionUtils.isNotEmpty(tagInfoDao.find(tagInfo))) {
            return -1L;
        }
        tagInfo.setCreateTime(new Date());
        tagInfo.setModifyTime(new Date());
        tagInfo.setIsDelete(EnumDeleted.NOT_DELETED.val());
        if (tagInfoDao.insert(tagInfo) > 0) {
            return tagInfo.getId();
        }
        return -1L;
    }

    @Override
    public TagInfo findTagInfoByName(String tagName) {
        TagInfo tagInfo = new TagInfo();
        tagInfo.setTagName(tagName);
        List<TagInfo> tagInfos = tagInfoDao.find(tagInfo);
        if (!CollectionUtils.isEmpty(tagInfos)) {
            return tagInfos.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<TagInfo> listTagInfoByTradeNo(Map param) {
        return tagInfoDao.listTagInfoByTradeNo(param);
    }

    @Override
    public TagInfo getTagInfoById(Long id) {
        return tagInfoDao.getTagInfoById(id);
    }

    @Override
    public Long insertTagCostRef(TagCostRef tagCostRef) {
        tagCostRef.setCreateTime(new Date());
        tagCostRef.setModifyTime(new Date());
        tagCostRef.setIsDelete(EnumDeleted.NOT_DELETED.val());
        if (tagInfoDao.insertTagCostRef(tagCostRef) > 0) {
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
        return tagInfoDao.deleteCostTag(param);
    }

    @Override
    public Long deleteTagById(Long id) {
        Map<String, Object> param = new HashMap<>();
        param.put("tagId", id);
        param.put("modifyTime", new Date());
        tagInfoDao.deleteCostTagByTagId(param);
        return tagInfoDao.deleteTagById(param);
    }

}
