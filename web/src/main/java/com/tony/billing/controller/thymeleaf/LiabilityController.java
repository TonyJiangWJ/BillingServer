package com.tony.billing.controller.thymeleaf;

import com.tony.billing.controller.BaseController;
import com.tony.billing.dto.LiabilityDTO;
import com.tony.billing.entity.Liability;
import com.tony.billing.request.liability.LiabilityDetailRequest;
import com.tony.billing.request.liability.LiabilityUpdateRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.LiabilityDetailResponse;
import com.tony.billing.service.LiabilityService;
import com.tony.billing.util.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/2/22
 */
@Controller
@RequestMapping("/thymeleaf")
public class LiabilityController extends BaseController {

    @Resource
    private LiabilityService liabilityService;

    @ResponseBody
    @RequestMapping("/liability/detail/get")
    public LiabilityDetailResponse getLiabilityInfo(@ModelAttribute("request") LiabilityDetailRequest request) {
        LiabilityDTO liability = liabilityService.getLiabilityInfoById(request.getId());
        if (liability != null) {
            LiabilityDetailResponse response = (LiabilityDetailResponse) ResponseUtil.success(new LiabilityDetailResponse());
            response.setLiability(liability);
            return response;
        }
        return (LiabilityDetailResponse) ResponseUtil.dataNotExisting(new LiabilityDetailResponse());
    }

    @ResponseBody
    @RequestMapping("/liability/update")
    public BaseResponse updateLiability(@ModelAttribute("request") LiabilityUpdateRequest request) {
        Liability update = new Liability();
        update.setId(request.getId());
        update.setAmount(request.getAmount());
        update.setPaid(request.getPaid());
        if (liabilityService.modifyLiabilityInfoById(update)) {
            return ResponseUtil.success();
        }
        return ResponseUtil.error();
    }
}
