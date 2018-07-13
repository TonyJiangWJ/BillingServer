package com.tony.billing.controller;

import com.tony.billing.dto.LiabilityDTO;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.entity.Liability;
import com.tony.billing.request.liability.LiabilityDetailRequest;
import com.tony.billing.request.liability.LiabilityUpdateRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.liability.LiabilityDetailResponse;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiangwj20966 2018/2/22
 */
@Controller
@RequestMapping("/bootDemo")
public class LiabilityController extends BaseController {

    @Resource
    private LiabilityService liabilityService;

    @Resource
    private AssetTypesService assetTypesService;

    @ResponseBody
    @RequestMapping("/liability/detail/get")
    public LiabilityDetailResponse getLiabilityInfo(@ModelAttribute("request") LiabilityDetailRequest request) {
        if (request.getId() == null) {
            return (LiabilityDetailResponse) ResponseUtil.paramError(new LiabilityDetailResponse());
        }
        Liability liability = liabilityService.getLiabilityInfoById(request.getId());
        if (liability != null && liability.getUserId().equals(request.getUserId())) {
            LiabilityDetailResponse response = (LiabilityDetailResponse) ResponseUtil.success(new LiabilityDetailResponse());

            response.setLiability(fillDTOWithType(liability));
            return response;
        }
        return (LiabilityDetailResponse) ResponseUtil.dataNotExisting(new LiabilityDetailResponse());
    }

    /**
     * 设置type
     * @param liability
     * @return
     */
    private LiabilityDTO fillDTOWithType(Liability liability) {

        LiabilityDTO liabilityDTO = new LiabilityDTO(liability);
        AssetTypes assetTypes = assetTypesService.selectById(liability.getType(), liability.getUserId());
        if(assetTypes!=null) {
            liabilityDTO.setType(assetTypes.getTypeDesc());
        }
        return liabilityDTO;
    }

    @ResponseBody
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
