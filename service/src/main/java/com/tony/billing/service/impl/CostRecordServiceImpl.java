package com.tony.billing.service.impl;

import com.alibaba.fastjson.JSON;
import com.tony.billing.dao.CostRecordDao;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.PagerGrid;
import com.tony.billing.entity.query.CostRecordQuery;
import com.tony.billing.service.CostRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 on 2017/6/2.
 */
@Service
public class CostRecordServiceImpl implements CostRecordService {

    @Resource
    private CostRecordDao costRecordDao;

    @Override
    public List<CostRecord> find(CostRecord record) {
        return costRecordDao.find(record);
    }

    @Override
    public PagerGrid<CostRecordQuery> page(PagerGrid<CostRecordQuery> pagerGrid) {
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

        List<CostRecordQuery> list = costRecordDao.page(params);
        Integer count = costRecordDao.count(params);
        pagerGrid.setResult(list);
        pagerGrid.setCount(count);
        return pagerGrid;
    }


    @Override
    public CostRecord findByTradeNo(String tradeNo, Long userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("tradeNo", tradeNo);
        params.put("userId", userId);
        return costRecordDao.findByTradeNo(params);
    }

    @Override
    public Integer toggleDeleteStatus(Map<String, Object> params) {
        return costRecordDao.toggleDeleteStatus(params);
    }

    @Override
    public Integer toggleHideStatus(Map<String, Object> params) {
        return costRecordDao.toggleHideStatus(params);
    }

    @Override
    public Long orderPut(CostRecord record) {
        Map<String, Object> params = new HashMap<>();
        params.put("tradeNo", record.getTradeNo());
        params.put("userId", record.getUserId());
        if (costRecordDao.findByTradeNo(params) != null) {
            return -1L;
        } else {
            if (costRecordDao.insert(record) > 0) {
                return record.getId();
            } else {
                return -1L;
            }
        }
    }

    @Override
    public Integer updateByTradeNo(CostRecord record) {
        return costRecordDao.updateByTradeNo(record);
    }
}
