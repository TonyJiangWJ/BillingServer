package com.tony.billing.service.impl;

import com.alibaba.fastjson.JSON;
import com.tony.billing.constants.enums.EnumLiabilityStatus;
import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.dao.AssetTypesDao;
import com.tony.billing.dao.LiabilityDao;
import com.tony.billing.dto.LiabilityDTO;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.entity.Liability;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.model.MonthLiabilityModel;
import com.tony.billing.service.LiabilityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LiabilityServiceImpl implements LiabilityService {

    @Resource
    private LiabilityDao liabilityDao;
    @Resource
    private AssetTypesDao assetTypesDao;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Liability> listLiabilityByUserId(Long userId) {
        Liability query = new Liability();
        query.setUserId(userId);
        return liabilityDao.list(query);
    }

    /**
     * 获取总负债信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<LiabilityModel> getLiabilityModelsByUserId(Long userId) {
        assert userId != null;
        Liability query = new Liability();
        query.setUserId(userId);
        query.setStatus(0);
        List<Liability> liabilities = liabilityDao.list(query);
        Map<String, AssetTypes> parentTypeMap = new HashMap<>();
        Map<Integer, AssetTypes> assetTypesMap = new HashMap<>();
        fillAssetTypeMap(liabilities, parentTypeMap, assetTypesMap, userId);
        List<LiabilityModel> allModels = fillLiabilityModels(liabilities, parentTypeMap, assetTypesMap);
        return mergeLiabilityByType(allModels);
    }

    private List<LiabilityModel> mergeLiabilityByType(List<LiabilityModel> allModels) {
        for(LiabilityModel model:allModels) {
            Map<String, LiabilityDTO> liabilityDTOMap = new HashMap<>();
            for(LiabilityDTO liabilityDTO:model.getLiabilityList()){
                LiabilityDTO mergedDto = null;
                if((mergedDto=liabilityDTOMap.get(liabilityDTO.getType()))!=null) {
                    mergedDto.setAmount(liabilityDTO.getAmount()-liabilityDTO.getPaid()+mergedDto.getAmount());
                } else {
                    mergedDto = new LiabilityDTO();
                    BeanUtils.copyProperties(liabilityDTO, mergedDto);
                    mergedDto.setAmount(liabilityDTO.getAmount()-liabilityDTO.getPaid());
                    liabilityDTOMap.put(liabilityDTO.getType(), mergedDto);
                }
            }
            List<LiabilityDTO> mergedList = new ArrayList<>();
            for(Map.Entry<String, LiabilityDTO> entry:liabilityDTOMap.entrySet()) {
                mergedList.add(entry.getValue());
            }
            model.setLiabilityList(mergedList);
        }
        return allModels;
    }

    private List<LiabilityModel> fillLiabilityModels(List<Liability> liabilities, Map<String, AssetTypes> parentTypeMap, Map<Integer, AssetTypes> assetTypesMap) {
        Map<String, LiabilityModel> modelMap = new HashMap<>();
        for (Map.Entry<String, AssetTypes> entry : parentTypeMap.entrySet()) {
            modelMap.put(entry.getKey(), new LiabilityModel(entry.getValue().getTypeDesc()));
        }
        List<LiabilityModel> liabilityModels = new ArrayList<>();
        LiabilityModel model;
        for (Liability liability : liabilities) {
            AssetTypes type = assetTypesMap.get(liability.getType());

            if (StringUtils.isNotEmpty(type.getParentCode())) {
                model = modelMap.get(type.getParentCode());
            } else {
                model = modelMap.get(type.getTypeCode());
            }
            model.setTotal(liability.getAmount() + model.getTotal() - liability.getPaid());
            model.getLiabilityList().add(new LiabilityDTO(liability, type.getTypeDesc()));
        }

        for (Map.Entry<String, LiabilityModel> modelEntry : modelMap.entrySet()) {
            liabilityModels.add(modelEntry.getValue());
        }

        return liabilityModels;
    }

    @Override
    public List<MonthLiabilityModel> getMonthLiabilityModelsByUserId(Long userId) {
        Liability query = new Liability();
        query.setUserId(userId);
        query.setStatus(0);
        List<Liability> liabilities = liabilityDao.list(query);
        Collections.sort(liabilities);
        SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
        String month = null;
        List<MonthLiabilityModel> monthLiabilityModels = new ArrayList<>();
        MonthLiabilityModel monthLiabilityModel = null;

        Map<String, List<Liability>> monthMap = new HashMap<>();
        for (Liability liability : liabilities) {
            month = monthFormat.format(liability.getRepaymentDay());
            if (monthMap.get(month) == null) {
                monthMap.put(month, new ArrayList<Liability>());
            }
            monthMap.get(month).add(liability);
        }

        for (Map.Entry<String, List<Liability>> entry : monthMap.entrySet()) {
            month = entry.getKey();
            monthLiabilityModel = new MonthLiabilityModel(month);

            Map<String, AssetTypes> parentTypeMap = new HashMap<>();
            Map<Integer, AssetTypes> assetTypesMap = new HashMap<>();
            fillAssetTypeMap(entry.getValue(), parentTypeMap, assetTypesMap, userId);
            monthLiabilityModel.setLiabilityModels(fillLiabilityModels(entry.getValue(), parentTypeMap, assetTypesMap));
            countDownMonthAmount(monthLiabilityModel);
            monthLiabilityModels.add(monthLiabilityModel);
        }

        return monthLiabilityModels;
    }

    public void fillAssetTypeMap(List<Liability> liabilities, Map<String, AssetTypes> parentTypeMap, Map<Integer, AssetTypes> assetTypesMap, Long userId) {
        for (Liability liability : liabilities) {
            if (assetTypesMap.get(liability.getType()) == null) {
                AssetTypes assetTypes = assetTypesDao.selectById(liability.getType());
                if (assetTypes != null) {
                    assetTypesMap.put(liability.getType(), assetTypes);
                    if (StringUtils.isNotEmpty(assetTypes.getParentCode())) {
                        if (parentTypeMap.get(assetTypes.getParentCode()) == null) {
                            assetTypes = assetTypesDao.getByTypeCode(assetTypes.getParentCode(), EnumTypeIdentify.LIABILITY.getIdentify(), userId);
                            if (assetTypes != null) {
                                parentTypeMap.put(assetTypes.getTypeCode(), assetTypes);
                            }
                        } else {
                            parentTypeMap.put(assetTypes.getTypeCode(), assetTypes);
                        }
                    }
                }
            }
        }
    }

    @Override
    public Liability getLiabilityInfoById(Long id) {
        return liabilityDao.getLiabilityById(id);
    }

    @Override
    public boolean modifyLiabilityInfoById(Liability liability) {
        if (liability.getAmount().equals(liability.getPaid())) {
            liability.setStatus(EnumLiabilityStatus.PAID.getStatus());
        }
        return liabilityDao.update(liability) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createLiabilityInfo(Liability liability) throws SQLException {
        Date repaymentDay = liability.getRepaymentDay();
        int installment = liability.getInstallment();

        AssetTypes assetTypes = assetTypesDao.selectById(liability.getType());
        if (assetTypes != null && (assetTypes.getUserId().equals(liability.getUserId()) || assetTypes.getUserId().equals(-1L))) {
            liability.setName(assetTypes.getTypeDesc());
        } else {
            logger.error("assetTypes不存在或者权限不足 id:{} userId:{}", liability.getType(), liability.getUserId());
            return false;
        }

        if (installment == 1) {
            liability.setIndex(1);
            return liabilityDao.insert(liability) > 0;
        } else {
            Long totalAmount = liability.getAmount();
            Long perInstallmentAmount = totalAmount / installment;
            long overflow = 0L;
            if (totalAmount % installment != 0) {
                overflow = totalAmount - perInstallmentAmount * installment;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(repaymentDay);
            List<Liability> newLiabilities = new ArrayList<>();
            Liability newRecord = null;

            for (int i = 1; i <= installment; i++) {
                newRecord = new Liability();
                if (installment == i && overflow != 0) {
                    newRecord.setAmount(perInstallmentAmount + overflow);
                } else {
                    newRecord.setAmount(perInstallmentAmount);
                }
                newRecord.setIndex(i);
                newRecord.setInstallment(installment);
                newRecord.setName(liability.getName());
                newRecord.setRepaymentDay(calendar.getTime());
                newRecord.setType(liability.getType());
                newRecord.setUserId(liability.getUserId());
                newLiabilities.add(newRecord);
                if (liabilityDao.insert(newRecord) <= 0) {
                    throw new SQLException("error insert");
                }
                calendar.add(Calendar.MONTH, 1);
            }
            logger.info("new Inserted:{}", JSON.toJSONString(newLiabilities));
        }
        return true;
    }


    private void countDownMonthAmount(MonthLiabilityModel model) {
        long sum = 0;
        for (LiabilityModel liabilityModel : model.getLiabilityModels()) {
            sum += liabilityModel.getTotal();
        }
        model.setTotal(sum);
    }
}
