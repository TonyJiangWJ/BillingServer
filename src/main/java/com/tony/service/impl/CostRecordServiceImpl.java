package com.tony.service.impl;

import com.tony.dao.CostRecordDao;
import com.tony.entity.CostRecord;
import com.tony.service.CostRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
}
