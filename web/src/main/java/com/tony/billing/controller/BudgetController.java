package com.tony.billing.controller;

import com.tony.billing.entity.Budget;
import com.tony.billing.request.budget.BudgetListRequest;
import com.tony.billing.request.budget.BudgetPutRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.budget.BudgetListResponse;
import com.tony.billing.service.BudgetService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Author jiangwj20966 on 2017/7/13.
 */
@RestController
@RequestMapping("/bootDemo")
public class BudgetController extends BaseController {
    @Resource
    private BudgetService budgetService;

    @RequestMapping("/budget/put")
    public BaseResponse addBudget(@ModelAttribute("request") BudgetPutRequest request) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getYear())
                || request.getBudgetMoney() == null
                || request.getMonth() == null
                || request.getTagId() == null
                || request.getUserId() == null) {
            return ResponseUtil.paramError(response);
        }
        try {
            Budget budget = new Budget();
            budget.setBelongMonth(request.getMonth());
            budget.setBelongYear(request.getYear());
            budget.setBudgetMoney(request.getBudgetMoney());
            budget.setTagId(request.getTagId());
            budget.setUserId(request.getUserId());
            if (budgetService.saveBudget(budget) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/budget/put error", e);
        }
        return response;
    }

    @RequestMapping("/budget/list")
    public BudgetListResponse listBudget(@ModelAttribute("request") BudgetListRequest request) {
        BudgetListResponse response = new BudgetListResponse();
        return null;
    }
}
