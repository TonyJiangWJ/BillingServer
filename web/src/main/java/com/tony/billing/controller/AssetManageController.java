package com.tony.billing.controller;

import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.dto.AssetDTO;
import com.tony.billing.dto.AssetManageDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.entity.Liability;
import com.tony.billing.model.AssetModel;
import com.tony.billing.model.AssetTypeModel;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.model.MonthLiabilityModel;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.request.asset.AssetDetailRequest;
import com.tony.billing.request.asset.AssetUpdateRequest;
import com.tony.billing.request.liability.LiabilityAddRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.asset.AssetDetailResponse;
import com.tony.billing.response.asset.AssetManageResponse;
import com.tony.billing.response.asset.AssetTypeResponse;
import com.tony.billing.service.AssetService;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TonyJiang on 2018/2/12
 */
@RestController
public class AssetManageController extends BaseController {

    @Resource
    private AssetService assetService;
    @Resource
    private AssetTypesService assetTypesService;
    @Resource
    private LiabilityService liabilityService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/asset/manage")
    public AssetManageResponse assetManage(@ModelAttribute("request") BaseRequest request) {
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
        AssetManageResponse response = (AssetManageResponse) ResponseUtil.success(new AssetManageResponse());
        response.setAssetManage(assetManageDTO);
        response.setAssetParentTypeList(transferToModel(assetTypesService.selectAssetTypeList(request.getUserId())));
        response.setLiabilityParentTypeList(transferToModel(assetTypesService.selectLiabilityTypeList(request.getUserId())));
        return response;
    }

    private List<AssetTypeModel> transferToModel(List<AssetTypes> assetTypes) {
        List<AssetTypeModel> assetTypeModels = new ArrayList<>();
        for (AssetTypes assetType : assetTypes) {
            assetTypeModels.add(new AssetTypeModel(assetType));
        }
        return assetTypeModels;
    }

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

    @RequestMapping("/list/asset/type/by/{identify}/{parentType}")
    public AssetTypeResponse listLiabilityTypeByParent(@PathVariable("parentType") String parentType,
                                                       @PathVariable("identify") String identify,
                                                       @ModelAttribute("request") BaseRequest request) {
        AssetTypeResponse response = (AssetTypeResponse) ResponseUtil.success(new AssetTypeResponse());
        if(EnumTypeIdentify.LIABILITY.getIdentify().equalsIgnoreCase(identify)) {
            response.setAssetTypes(transferToModel(assetTypesService.selectAssetTypeListByParent(parentType, request.getUserId())));
        } else {
            response.setAssetTypes(transferToModel(assetTypesService.selectLiabilityTypeListByParent(parentType, request.getUserId())));
        }
        return response;
    }


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
