package com.tony.billing.dao.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.dao.CostRecordDao;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.dao.mapper.CostRecordMapper;
import com.tony.billing.entity.query.CostRecordQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author by TonyJiang on 2017/7/12.
 */
@Service("costRecordDao")
public class CostRecordDaoImpl implements CostRecordDao {

    @Resource
    private CostRecordMapper costRecordMapper;

    @Override
    public List<CostRecord> find(CostRecord record) {
        return costRecordMapper.find(record);
    }

    @Override
    public List<CostRecordQuery> page(Map<String, Object> params) {
        Preconditions.checkNotNull(params.get("userId"),"userId must not be null");
        return costRecordMapper.page(params);
    }

    @Override
    public Integer count(Map<String, Object> params) {
        Preconditions.checkNotNull(params.get("userId"),"userId must not be null");
        return costRecordMapper.count(params);
    }

    @Override
    public CostRecord findByTradeNo(Map<String, Object> params) {
        Preconditions.checkNotNull(params.get("userId"),"userId must not be null");
        return costRecordMapper.findByTradeNo(params);
    }

    @Override
    public Long insert(CostRecord record) {
        Preconditions.checkNotNull(record.getUserId(),"userId must not be null");
        Preconditions.checkArgument(StringUtils.isNotEmpty(record.getTradeNo()),"tradeNo must not be empty");
        return costRecordMapper.insert(record);
    }

    @Override
    public Integer toggleDeleteStatus(Map<String, Object> params) {
        return costRecordMapper.toggleDeleteStatus(params);
    }

    @Override
    public Integer toggleHideStatus(Map<String, Object> params) {

        return costRecordMapper.toggleHideStatus(params);
    }

    @Override
    public Integer updateByTradeNo(CostRecord record) {
        return costRecordMapper.updateByTradeNo(record);
    }

    @Override
    public List<CostRecord> findByTagId(Map<String, Object> params) {
        return costRecordMapper.findByTagId(params);
    }
}
