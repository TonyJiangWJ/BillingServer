package com.tony.billing.controller.thymeleaf;

import com.tony.billing.controller.BaseController;
import com.tony.billing.dto.AssetManageDTO;
import com.tony.billing.model.AssetModel;
import com.tony.billing.model.LiabilityModel;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.service.AssetService;
import com.tony.billing.service.LiabilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;

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
        model.addAttribute("assetManageDTO", assetManageDTO);
        return "/thymeleaf/asset/manage";
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

}
