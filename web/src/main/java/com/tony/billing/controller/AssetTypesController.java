package com.tony.billing.controller;

import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.model.AssetTypeModel;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.response.asset.AssetTypeResponse;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.util.ResponseUtil;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangwj20966 6/25/2018
 */
@RestController
public class AssetTypesController extends BaseController {

    @Resource
    private AssetTypesService assetTypesService;


    @RequestMapping("/list/asset/type/by/{identify}/{parentType}")
    public AssetTypeResponse listAssetTypesByParent(@PathVariable("parentType") String parentType,
                                                       @PathVariable("identify") String identify,
                                                       @ModelAttribute("request") BaseRequest request) {
        AssetTypeResponse response = (AssetTypeResponse) ResponseUtil.success(new AssetTypeResponse());
        if (EnumTypeIdentify.LIABILITY.getIdentify().equalsIgnoreCase(identify)) {
            response.setAssetTypes(transferToModel(assetTypesService.selectAssetTypeListByParent(parentType, request.getUserId())));
        } else {
            response.setAssetTypes(transferToModel(assetTypesService.selectLiabilityTypeListByParent(parentType, request.getUserId())));
        }
        return response;
    }

    @RequestMapping("/list/asset/parent/types/{identify}")
    public AssetTypeResponse listAssetParentTypes(@PathVariable("identify") String identify, @ModelAttribute("request") BaseRequest request) {
        AssetTypeResponse response = (AssetTypeResponse) ResponseUtil.success(new AssetTypeResponse());
        if (EnumTypeIdentify.LIABILITY.getIdentify().equalsIgnoreCase(identify)) {
            response.setAssetTypes(transferToModel(assetTypesService.selectAssetTypeList(request.getUserId())));
        } else {
            response.setAssetTypes(transferToModel(assetTypesService.selectLiabilityTypeList(request.getUserId())));
        }
        return response;
    }

    private List<AssetTypeModel> transferToModel(List<AssetTypes> assetTypes) {
        List<AssetTypeModel> assetTypeModels = new ArrayList<>();
        for (AssetTypes assetType : assetTypes) {
            assetTypeModels.add(new AssetTypeModel(assetType));
        }
        return assetTypeModels;
    }
}
