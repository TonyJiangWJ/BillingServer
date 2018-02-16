package com.tony.billing.service;

import com.tony.billing.entity.Liability;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.model.MonthLiabilityModel;

import java.util.List;

public interface LiabilityService {

    List<Liability> listLiabilityByUserId(Long userId);

    /**
     * 获取总负债信息
     * @param userId
     * @return
     */
    List<LiabilityModel> getLiabilityModelsByUserId(Long userId);

    /**
     * 获取每月分期还款信息
     * @param userId
     * @return
     */
    List<MonthLiabilityModel> getMonthLiabilityModelsByUserId(Long userId);
}
