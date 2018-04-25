package com.tony.billing.service.impl;

import com.alibaba.fastjson.JSON;
import com.tony.billing.constants.enums.EnumLiabilityParentType;
import com.tony.billing.constants.enums.EnumLiabilityStatus;
import com.tony.billing.constants.enums.EnumLiabilityType;
import com.tony.billing.dao.LiabilityDao;
import com.tony.billing.dto.LiabilityDTO;
import com.tony.billing.dto.LiabilityTypeDTO;
import com.tony.billing.entity.Liability;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.model.MonthLiabilityModel;
import com.tony.billing.service.LiabilityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LiabilityServiceImpl implements LiabilityService {

    @Resource
    private LiabilityDao liabilityDao;

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
        Liability query = new Liability();
        query.setUserId(userId);
        query.setStatus(0);
        List<Liability> liabilities = liabilityDao.list(query);
        List<LiabilityModel> liabilityModels = new ArrayList<>();
        for (Liability liability : liabilities) {
            insertIntoModels(liabilityModels, liability);
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
        boolean firstInsert = true;
        List<MonthLiabilityModel> monthLiabilityModels = new ArrayList<>();
        MonthLiabilityModel monthLiabilityModel = null;
        for (Liability liability : liabilities) {
            if (firstInsert) {
                month = monthFormat.format(liability.getRepaymentDay());
                monthLiabilityModel = new MonthLiabilityModel();
                monthLiabilityModel.setMonth(month);
                insertIntoModelsNoMerge(monthLiabilityModel.getLiabilityModels(), liability);
                monthLiabilityModels.add(monthLiabilityModel);
                firstInsert = false;
            } else {
                if (month.equals(monthFormat.format(liability.getRepaymentDay()))) {
                    // insert
                    insertIntoModelsNoMerge(monthLiabilityModel.getLiabilityModels(), liability);
                } else {
                    // 计算总金额
                    countDownMonthAmount(monthLiabilityModel);
                    month = monthFormat.format(liability.getRepaymentDay());
                    monthLiabilityModel = new MonthLiabilityModel();
                    monthLiabilityModel.setMonth(month);
                    insertIntoModelsNoMerge(monthLiabilityModel.getLiabilityModels(), liability);
                    monthLiabilityModels.add(monthLiabilityModel);
                }
            }
        }
        if (monthLiabilityModel != null) {
            // 计算总金额
            countDownMonthAmount(monthLiabilityModel);
        }
        return monthLiabilityModels;
    }

    @Override
    public LiabilityDTO getLiabilityInfoById(Long id) {
        Liability liability = liabilityDao.getLiabilityById(id);
        return new LiabilityDTO(liability);
    }

    @Override
    public boolean modifyLiabilityInfoById(Liability liability) {
        if (liability.getAmount().equals(liability.getPaid())) {
            liability.setStatus(EnumLiabilityStatus.PAID.getStatus());
        }
        return liabilityDao.update(liability) > 0;
    }

    @Override
    @Transactional
    public boolean createLiabilityInfo(Liability liability) throws SQLException {
        Date repaymentDay = liability.getRepaymentDay();
        int installment = liability.getInstallment();
        liability.setName(EnumLiabilityType.getEnumByType(liability.getType()).getDesc());
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
                newRecord.setParentType(liability.getParentType());
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

    @Override
    public List<LiabilityTypeDTO> getLiabilityTypesByParent(String parentType) {

        List<LiabilityTypeDTO> liabilityTypes = new ArrayList<>();
        for (EnumLiabilityType enumLiabilityType : EnumLiabilityType.values()) {
            if (StringUtils.equals(parentType, enumLiabilityType.getParentType())) {
                liabilityTypes.add(new LiabilityTypeDTO(enumLiabilityType.getDesc(), enumLiabilityType.getType(), enumLiabilityType.getParentType()));
            }
        }
        return liabilityTypes;
    }

    private void insertIntoModels(List<LiabilityModel> liabilityModels, Liability liability) {
        boolean inserted = false;
        for (LiabilityModel model : liabilityModels) {
            if (StringUtils.equals(model.getType(), liability.getParentType())) {
                insertIntoDTOList(model.getLiabilityList(), liability);
                model.setTotal(model.getTotal() + liability.getAmount() - liability.getPaid());
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            LiabilityModel liabilityModel = new LiabilityModel();
            insertIntoDTOList(liabilityModel.getLiabilityList(), liability);
            liabilityModel.setTotal(liabilityModel.getTotal() + liability.getAmount() - liability.getPaid());
            liabilityModel.setType(liability.getParentType());
            liabilityModel.setName(EnumLiabilityParentType.getEnumByType(liability.getParentType()).getDesc());
            liabilityModels.add(liabilityModel);
        }
    }

    private void insertIntoModelsNoMerge(List<LiabilityModel> liabilityModels, Liability liability) {
        boolean inserted = false;
        for (LiabilityModel model : liabilityModels) {
            if (StringUtils.equals(model.getType(), liability.getParentType())) {
                model.getLiabilityList().add(new LiabilityDTO(liability));
                model.setTotal(model.getTotal() + liability.getAmount() - liability.getPaid());
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            LiabilityModel liabilityModel = new LiabilityModel();
            liabilityModel.getLiabilityList().add(new LiabilityDTO(liability));
            liabilityModel.setTotal(liabilityModel.getTotal() + liability.getAmount() - liability.getPaid());
            liabilityModel.setType(liability.getParentType());
            liabilityModel.setName(EnumLiabilityParentType.getEnumByType(liability.getParentType()).getDesc());
            liabilityModels.add(liabilityModel);
        }
    }

    /**
     * 合并插入
     *
     * @param dtoList
     * @param liability
     */
    private void insertIntoDTOList(List<LiabilityDTO> dtoList, Liability liability) {
        boolean inserted = false;
        for (LiabilityDTO dto : dtoList) {
            if (StringUtils.equals(dto.getType(), liability.getType())) {
                dto.setAmount(dto.getAmount() + liability.getAmount() - liability.getPaid());
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            dtoList.add(new LiabilityDTO(liability));
        }
    }

    private void countDownMonthAmount(MonthLiabilityModel model) {
        long sum = 0;
        for (LiabilityModel liabilityModel : model.getLiabilityModels()) {
            sum += liabilityModel.getTotal();
        }
        model.setTotal(sum);
    }
}
