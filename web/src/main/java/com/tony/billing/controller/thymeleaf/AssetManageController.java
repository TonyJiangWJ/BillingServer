package com.tony.billing.controller.thymeleaf;

import com.tony.billing.constants.enums.EnumLiabilityParentType;
import com.tony.billing.controller.BaseController;
import com.tony.billing.dto.AssetDTO;
import com.tony.billing.dto.AssetManageDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.entity.Liability;
import com.tony.billing.model.AssetModel;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.model.MonthLiabilityModel;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.request.asset.AssetDetailRequest;
import com.tony.billing.request.asset.AssetUpdateRequest;
import com.tony.billing.request.liability.LiabilityAddRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.asset.AssetDetailResponse;
import com.tony.billing.response.liability.LiabilityTypeResponse;
import com.tony.billing.service.AssetService;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author TonyJiang on 2018/2/12
 */
@Controller
@RequestMapping("/thymeleaf")
public class AssetManageController extends BaseController {

    @Resource
    private AssetService assetService;

    @Resource
    private LiabilityService liabilityService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/asset/manage")
    public String assetManage(@ModelAttribute("request") BaseRequest request, Model model) {
        AssetManageDTO assetManageDTO = new AssetManageDTO();
        // 计算总资产
        assetManageDTO.setAssetModels(assetService.getAssetModelsByUserId(request.getUserId()));
        assetManageDTO.setTotalAsset(getTotalAsset(assetManageDTO.getAssetModels()));
        // 计算总负债
        assetManageDTO.setLiabilityModels(liabilityService.getLiabilityModelsByUserId(request.getUserId()));
        assetManageDTO.setTotalLiability(getTotalLiability(assetManageDTO.getLiabilityModels()));
        // 计算净资产
        assetManageDTO.setCleanAsset(assetManageDTO.getTotalAsset() - assetManageDTO.getTotalLiability());
        // 计算每月还款信息
        assetManageDTO.setMonthLiabilityModels(liabilityService.getMonthLiabilityModelsByUserId(request.getUserId()));
        // 计算每月还款后剩余
        assetManageDTO = calAssetAfterMonth(assetManageDTO);
        model.addAttribute("assetManageDTO", assetManageDTO);
        model.addAttribute("liabilityParentList", EnumLiabilityParentType.toList());
        return "/thymeleaf/asset/manage";
    }

    @ResponseBody
    @RequestMapping("/asset/detail/get")
    public AssetDetailResponse getAssetDetail(@ModelAttribute("request") AssetDetailRequest request, Model model) {
        AssetDetailResponse response = (AssetDetailResponse) ResponseUtil.success(new AssetDetailResponse());
        AssetDTO assetDTO = assetService.getAssetInfoById(request.getId());
        if (assetDTO != null) {
            response.setAssetInfo(assetDTO);
        } else {
            response = (AssetDetailResponse) ResponseUtil.dataNotExisting(new AssetDetailResponse());
        }
        return response;
    }

    @ResponseBody
    @RequestMapping("/asset/update")
    public BaseResponse updateAsset(@ModelAttribute("request") AssetUpdateRequest request) {
        Asset update = new Asset();
        update.setId(request.getId());
        update.setAmount(request.getAmount());
        if (assetService.modifyAssetInfoById(update)) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error();
        }
    }

    @ResponseBody
    @RequestMapping("/list/liability/type/by/parent/{parentType}")
    public LiabilityTypeResponse listLiabilityTypeByParent(@PathVariable("parentType") String parentType) {
        LiabilityTypeResponse response = (LiabilityTypeResponse) ResponseUtil.success(new LiabilityTypeResponse());
        response.setLiabilityTypes(liabilityService.getLiabilityTypesByParent(parentType));
        return response;
    }

    @ResponseBody
    @RequestMapping(value = "/liability/put", method = RequestMethod.POST)
    public BaseResponse addLiability(@ModelAttribute("request") LiabilityAddRequest request) {
        Liability liability = new Liability();
        liability.setRepaymentDay(request.getRepaymentDay());
        liability.setParentType(request.getParentType());
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

    private Long getTotalLiability(List<LiabilityModel> liabilityModels) {
        Long sum = 0L;
        for (LiabilityModel liabilityModel : liabilityModels) {
            sum += liabilityModel.getTotal();
        }
        return sum;
    }

    private Long getTotalAsset(List<AssetModel> assetModels) {
        Long sum = 0L;
        for (AssetModel assetModel : assetModels) {
            sum += assetModel.getTotal();
        }
        return sum;
    }

    private AssetManageDTO calAssetAfterMonth(AssetManageDTO assetManageDTO) {
        Long totalAsset = assetManageDTO.getTotalAsset();
        if (CollectionUtils.isNotEmpty(assetManageDTO.getMonthLiabilityModels())) {
            for (MonthLiabilityModel model : assetManageDTO.getMonthLiabilityModels()) {
                totalAsset -= model.getTotal();
                model.setAssetAfterThisMonth(totalAsset);
            }
        }
        return assetManageDTO;
    }

}
