package com.tony.billing.controller;

import com.tony.billing.constants.enums.EnumTypeIdentify;
import com.tony.billing.entity.AssetTypes;
import com.tony.billing.model.AssetTypeModel;
import com.tony.billing.request.assettypes.AssetTypeAddRequest;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.asset.AssetTypeResponse;
import com.tony.billing.service.AssetTypesService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangwj20966 6/25/2018
 */
@RestController
@RequestMapping("/bootDemo")
public class AssetTypesController extends BaseController {

    @Resource
    private AssetTypesService assetTypesService;


    /**
     * 根据父类型获取子类别列表
     *
     * @param parentType 父类型
     * @param identify   资产或负债
     * @param request    请求体
     * @return 返回子类别列表
     */
    @RequestMapping("/list/asset/type/by/{identify}/{parentType}")
    public AssetTypeResponse listAssetTypesByParent(@PathVariable("parentType") String parentType,
                                                    @PathVariable("identify") String identify,
                                                    @ModelAttribute("request") BaseRequest request) {
        AssetTypeResponse response = (AssetTypeResponse) ResponseUtil.success(new AssetTypeResponse());
        if (EnumTypeIdentify.LIABILITY.getIdentify().equalsIgnoreCase(identify)) {
            response.setAssetTypes(transferToModel(assetTypesService.selectLiabilityTypeListByParent(parentType, request.getUserId())));
        } else {
            response.setAssetTypes(transferToModel(assetTypesService.selectAssetTypeListByParent(parentType, request.getUserId())));
        }
        return response;
    }

    @RequestMapping("/list/asset/type/by/parent/id")
    public AssetTypeResponse listAssetTypesByParentId(@RequestParam("id") Integer id, @ModelAttribute("request") BaseRequest request) {
        AssetTypes assetTypes = assetTypesService.selectById(id, request.getUserId());
        if (assetTypes != null && StringUtils.isEmpty(assetTypes.getParentCode())) {
            return listAssetTypesByParent(assetTypes.getTypeCode(), assetTypes.getTypeIdentify(), request);
        }
        return (AssetTypeResponse) ResponseUtil.dataNotExisting(new AssetTypeResponse());
    }

    /**
     * 获取父类别列表
     *
     * @param identify 资产或负债
     * @param request  request
     * @return 父类别列表
     */
    @RequestMapping("/list/asset/parent/types/{identify}")
    public AssetTypeResponse listAssetParentTypes(@PathVariable("identify") String identify, @ModelAttribute("request") BaseRequest request) {
        AssetTypeResponse response = (AssetTypeResponse) ResponseUtil.success(new AssetTypeResponse());
        if (EnumTypeIdentify.LIABILITY.getIdentify().equalsIgnoreCase(identify)) {
            response.setAssetTypes(transferToModel(assetTypesService.selectLiabilityTypeList(request.getUserId())));
        } else {
            response.setAssetTypes(transferToModel(assetTypesService.selectAssetTypeList(request.getUserId())));
        }
        return response;
    }

    @RequestMapping("/asset/types/put")
    public BaseResponse putAssetParentType(@ModelAttribute("request") AssetTypeAddRequest request) {
        AssetTypes assetTypes = new AssetTypes();
        assetTypes.setUserId(request.getUserId());
        assetTypes.setParentCode(request.getParentCode());
        assetTypes.setTypeIdentify(request.getTypeIdentify());
        assetTypes.setTypeCode(request.getTypeCode());
        assetTypes.setTypeDesc(request.getTypeDesc());
        if (assetTypesService.insert(assetTypes) > 0) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error();
        }
    }

    private List<AssetTypeModel> transferToModel(List<AssetTypes> assetTypes) {
        List<AssetTypeModel> assetTypeModels = new ArrayList<>();
        for (AssetTypes assetType : assetTypes) {
            assetTypeModels.add(new AssetTypeModel(assetType));
        }
        return assetTypeModels;
    }
}
