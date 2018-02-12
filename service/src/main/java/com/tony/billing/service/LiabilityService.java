package com.tony.billing.service;

import com.tony.billing.entity.Liability;
import com.tony.billing.model.LiabilityModel;

import java.util.List;

public interface LiabilityService {

    List<Liability> listLiabilityByUserId(Long userId);

    List<LiabilityModel> getLiabilityModelsByUserId(Long userId);

}
