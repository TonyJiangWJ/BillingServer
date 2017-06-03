package com.tony.service.impl;

import com.alibaba.fastjson.JSON;
import com.tony.dao.CostRecordDao;
import com.tony.entity.CostRecord;
import com.tony.entity.PagerGrid;
import com.tony.service.CostRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
@Service
public class CostRecordServiceImpl implements CostRecordService {

    @Resource
    private CostRecordDao costRecordDao;

    public List<CostRecord> find(CostRecord record) {
        return costRecordDao.find(record);
    }

    public PagerGrid<CostRecord> page(PagerGrid<CostRecord> pagerGrid) {
        Map<String, Object> params;
        if (pagerGrid.getT() == null)
            params = new HashMap<String, Object>();
        else
            params = JSON.parseObject(JSON.toJSONString(pagerGrid.getT()));
        params.put("index", pagerGrid.getIndex());
        params.put("offset", pagerGrid.getOffset());
        if (StringUtils.isNotBlank(pagerGrid.getOrderBy()))
            params.put("orderBy", pagerGrid.getOrderBy());
        if (StringUtils.isNoneBlank(pagerGrid.getSort()))
            params.put("sort", pagerGrid.getSort());

        List<CostRecord> list = costRecordDao.page(params);
        Integer count = costRecordDao.count(params);
        pagerGrid.setResult(list);
        pagerGrid.setCount(count);
        return pagerGrid;
    }

    public CostRecord findByTradeNo(String tradeNo) {
        return costRecordDao.findByTradeNo(tradeNo);
    }

    public Integer toggleDeleteStatus(Map<String, Object> params) {
        return costRecordDao.toggleDeleteStatus(params);
    }
}
