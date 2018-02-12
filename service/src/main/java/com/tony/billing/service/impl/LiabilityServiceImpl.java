package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumLiabilityParentType;
import com.tony.billing.dao.LiabilityDao;
import com.tony.billing.dto.LiabilityDTO;
import com.tony.billing.entity.Liability;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.service.LiabilityService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    private void insertIntoModels(List<LiabilityModel> liabilityModels, Liability liability) {
        boolean inserted = false;
        for (LiabilityModel model : liabilityModels) {
            if (StringUtils.equals(model.getType(), liability.getParentType())) {
                insertIntoDTOList(model.getLiabilityList(), liability);
                model.setTotal(model.getTotal() + liability.getAmount());
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            LiabilityModel liabilityModel = new LiabilityModel();
            insertIntoDTOList(liabilityModel.getLiabilityList(), liability);
            liabilityModel.setTotal(liabilityModel.getTotal() + liability.getAmount());
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
                dto.setAmount(dto.getAmount() + liability.getAmount());
                inserted = true;
                break;
            }
        }
        if (!inserted) {
            dtoList.add(bindDTO(liability));
        }
    }

    private LiabilityDTO bindDTO(Liability liability) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        LiabilityDTO liabilityDTO = new LiabilityDTO();
        liabilityDTO.setId(liability.getId());
        liabilityDTO.setAmount(liability.getAmount());
        liabilityDTO.setIndex(liability.getIndex());
        liabilityDTO.setInstallment(liability.getInstallment());
        liabilityDTO.setName(liability.getName());
        liabilityDTO.setRepaymentDay(simpleDateFormat.format(liability.getRepaymentDay()));
        liabilityDTO.setStatus(liability.getStatus());
        liabilityDTO.setType(liability.getType());
        return liabilityDTO;
    }
}
