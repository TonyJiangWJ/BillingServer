package com.tony.billing.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.tony.billing.dao.mapper.CostRecordMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.PagerGrid;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.service.base.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 on 2017/6/2.
 */
@Service
public class CostRecordServiceImpl extends AbstractService<CostRecord> implements CostRecordService {

    @Resource
    private CostRecordMapper costRecordMapper;

    @Override
    protected AbstractMapper<CostRecord> getMapper() {
        return costRecordMapper;
    }

    @Override
    public List<CostRecord> find(CostRecord record) {
        return super.list(record);
    }

    @Override
    public PagerGrid<CostRecord> page(PagerGrid<CostRecord> pagerGrid) {
        Map<String, Object> params;
        if (pagerGrid.getT() == null) {
            params = new HashMap<String, Object>();
        } else {
            params = JSON.parseObject(JSON.toJSONString(pagerGrid.getT()));
        }
        params.put("index", pagerGrid.getIndex());
        params.put("offset", pagerGrid.getOffset());
        if (StringUtils.isNotBlank(pagerGrid.getOrderBy())) {
            params.put("orderBy", pagerGrid.getOrderBy());
        }
        if (StringUtils.isNoneBlank(pagerGrid.getSort())) {
            params.put("sort", pagerGrid.getSort());
        }

        List<CostRecord> list = costRecordMapper.page(params);
        Integer count = costRecordMapper.count(params);
        pagerGrid.setResult(list);
        pagerGrid.setCount(count);
        return pagerGrid;
    }


    @Override
    public CostRecord findByTradeNo(String tradeNo, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("tradeNo", tradeNo);
        params.put("userId", userId);
        return costRecordMapper.findByTradeNo(params);
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
    public Long orderPut(CostRecord record) {
        Map<String, Object> params = new HashMap<>();
        params.put("tradeNo", record.getTradeNo());
        params.put("userId", record.getUserId());
        if (costRecordMapper.findByTradeNo(params) != null) {
            return -1L;
        } else {
            return super.insert(record);
        }
    }

    @Override
    public Integer updateByTradeNo(CostRecord record) {
        Preconditions.checkNotNull(record.getVersion(), "version must not be null");
        record.setModifyTime(new Date());
        return costRecordMapper.updateByTradeNo(record);
    }
}
