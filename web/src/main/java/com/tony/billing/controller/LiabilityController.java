package com.tony.billing.controller;

import com.tony.billing.dto.LiabilityDTO;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.entity.Liability;
import com.tony.billing.request.liability.LiabilityAddRequest;
import com.tony.billing.request.liability.LiabilityDetailRequest;
import com.tony.billing.request.liability.LiabilityUpdateRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.liability.LiabilityDetailResponse;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.util.ResponseUtil;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;

/**
 * @author jiangwj20966 2018/2/22
 */
@RestController
@RequestMapping("/bootDemo")
public class LiabilityController extends BaseController {

    @Resource
    private LiabilityService liabilityService;

    @Resource
    private AssetTypesService assetTypesService;

    @RequestMapping("/liability/detail/get")
    public LiabilityDetailResponse getLiabilityInfo(@ModelAttribute("request") LiabilityDetailRequest request) {
        if (request.getId() == null) {
            return ResponseUtil.paramError(new LiabilityDetailResponse());
        }
        Liability liability = liabilityService.getLiabilityInfoById(request.getId());
        if (liability != null && liability.getUserId().equals(request.getUserId())) {
            LiabilityDetailResponse response = ResponseUtil.success(new LiabilityDetailResponse());

            response.setLiability(fillDTOWithType(liability));
            return response;
        }
        return ResponseUtil.dataNotExisting(new LiabilityDetailResponse());
    }

    /**
     * 设置type
     * @param liability
     * @return
     */
    private LiabilityDTO fillDTOWithType(Liability liability) {
        LiabilityDTO liabilityDTO = new LiabilityDTO(liability);
        AssetTypes assetTypes = assetTypesService.selectById(liability.getType());
        if(assetTypes!=null) {
            liabilityDTO.setType(assetTypes.getTypeDesc());
        }
        return liabilityDTO;
    }

    @RequestMapping(value = "/liability/put", method = RequestMethod.POST)
    public BaseResponse addLiability(@ModelAttribute("request") LiabilityAddRequest request) {

        if (request.getRepaymentDay() == null
                || request.getType() == null
                || request.getInstallment() == null
                || request.getAmount() == null) {
            return ResponseUtil.paramError();
        }

        Liability liability = new Liability();
        liability.setRepaymentDay(request.getRepaymentDay());
        liability.setType(request.getType());
        liability.setAmount(request.getAmount());
        liability.setInstallment(request.getInstallment());
        liability.setUserId(request.getUserId());
        try {
            if (liabilityService.createLiabilityInfo(liability)) {
                return ResponseUtil.success();
            } else {
                return ResponseUtil.error();
            }
        } catch (SQLException e) {
            logger.error("create liability info error:", e);
            return ResponseUtil.sysError();
        }
    }

    @RequestMapping("/liability/update")
    public BaseResponse updateLiability(@ModelAttribute("request") LiabilityUpdateRequest request) {
        Liability update = new Liability();
        if (request.getId() == null || request.getAmount() == null) {
            return ResponseUtil.paramError();
        }
        update.setId(request.getId());
        update.setAmount(request.getAmount());
        update.setPaid(request.getPaid());
        update.setUserId(request.getUserId());
        if (liabilityService.modifyLiabilityInfoById(update)) {
            return ResponseUtil.success();
        }
        return ResponseUtil.error();
    }
}
