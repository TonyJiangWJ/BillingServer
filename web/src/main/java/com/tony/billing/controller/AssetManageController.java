package com.tony.billing.controller;

import com.tony.billing.dto.AssetDTO;
import com.tony.billing.dto.AssetManageDTO;
import com.tony.billing.entity.Asset;
import com.tony.billing.entity.Liability;
import com.tony.billing.model.AssetModel;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.model.MonthLiabilityModel;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.request.asset.AssetAddRequest;
import com.tony.billing.request.asset.AssetDetailRequest;
import com.tony.billing.request.asset.AssetUpdateRequest;
import com.tony.billing.request.liability.LiabilityAddRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.asset.AssetDetailResponse;
import com.tony.billing.response.asset.AssetManageResponse;
import com.tony.billing.service.AssetService;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author TonyJiang on 2018/2/12
 */
@RestController
@RequestMapping("/bootDemo")
public class AssetManageController extends BaseController {

    @Resource
    private AssetService assetService;
    @Resource
    private LiabilityService liabilityService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/asset/manage")
    public AssetManageResponse assetManage(@ModelAttribute("request") BaseRequest request) {
        AssetManageDTO assetManageDTO = new AssetManageDTO();
        // 计算总资产
        assetManageDTO.setAssetModels(assetService.getAssetModelsByUserId(request.getUserId()));
        assetManageDTO.setTotalAsset(assetManageDTO.getAssetModels()
                .stream().mapToLong(AssetModel::getTotal).sum());
        // 计算总负债
        assetManageDTO.setLiabilityModels(liabilityService.getLiabilityModelsByUserId(request.getUserId()));
        assetManageDTO.setTotalLiability(assetManageDTO.getLiabilityModels()
                .stream().mapToLong(LiabilityModel::getTotal).sum());
        // 计算净资产
        assetManageDTO.setCleanAsset(assetManageDTO.getTotalAsset() - assetManageDTO.getTotalLiability());
        // 计算可以直接使用的金额
        assetManageDTO.setAvailableAsset(assetManageDTO.getAssetModels()
                .stream().mapToLong(AssetModel::getTotalAvailable).sum());
        // 计算每月还款信息
        assetManageDTO.setMonthLiabilityModels(liabilityService.getMonthLiabilityModelsByUserId(request.getUserId()));
        // 计算每月还款后剩余
        calAssetAfterMonth(assetManageDTO);
        AssetManageResponse response = (AssetManageResponse) ResponseUtil.success(new AssetManageResponse());
        response.setAssetManage(assetManageDTO);
        return response;
    }

    @RequestMapping("/asset/detail/get")
    public AssetDetailResponse getAssetDetail(@ModelAttribute("request") AssetDetailRequest request, Model model) {
        AssetDetailResponse response = (AssetDetailResponse) ResponseUtil.success(new AssetDetailResponse());
        Asset asset = assetService.getAssetInfoById(request.getId());
        if (asset != null && asset.getUserId().equals(request.getUserId())) {
            response.setAssetInfo(new AssetDTO(asset));
        } else {
            response = (AssetDetailResponse) ResponseUtil.dataNotExisting(new AssetDetailResponse());
        }
        return response;
    }

    @RequestMapping("/asset/update")
    public BaseResponse updateAsset(@ModelAttribute("request") AssetUpdateRequest request) {
        Asset update = new Asset();
        if (request.getAmount() == null || request.getId() == null) {
            return ResponseUtil.paramError();
        }
        update.setId(request.getId());
        update.setAmount(request.getAmount());
        update.setExtName(request.getName());
        update.setUserId(request.getUserId());
        if (assetService.modifyAssetInfoById(update)) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error();
        }
    }

    @RequestMapping("/asset/put")
    public BaseResponse addAsset(@ModelAttribute("request") AssetAddRequest request) {

        if (request.getAmount() == null
                || request.getType() == null) {
            return ResponseUtil.paramError();
        }
        Asset asset = new Asset();
        asset.setAmount(request.getAmount());
        asset.setType(request.getType());
        asset.setUserId(request.getUserId());

        if (StringUtils.isNotEmpty(request.getName())) {
            asset.setExtName(request.getName());
        }
        if (assetService.addAsset(asset) > 0) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error();
        }
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

    private void calAssetAfterMonth(AssetManageDTO assetManageDTO) {
        Long totalAsset = assetManageDTO.getTotalAsset();
        if (CollectionUtils.isNotEmpty(assetManageDTO.getMonthLiabilityModels())) {
            for (MonthLiabilityModel model : assetManageDTO.getMonthLiabilityModels()) {
                totalAsset -= model.getTotal();
                model.setAssetAfterThisMonth(totalAsset);
            }
        }
    }

}
