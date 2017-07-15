package com.tony.billing.controller;

import com.tony.billing.dto.BudgetDto;
import com.tony.billing.entity.Budget;
import com.tony.billing.model.BudgetModel;
import com.tony.billing.request.budget.BudgetListRequest;
import com.tony.billing.request.budget.BudgetPutRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.budget.BudgetListResponse;
import com.tony.billing.service.BudgetService;
import com.tony.billing.util.MoneyUtil;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
            ResponseUtil.sysError(response);
        }
        return response;
    }

    @RequestMapping("/budget/list")
    public BudgetListResponse listBudget(@ModelAttribute("request") BudgetListRequest request) {
        BudgetListResponse response = new BudgetListResponse();
        if (StringUtils.isEmpty(request.getYear()) || request.getMonth() == null) {
            return (BudgetListResponse) ResponseUtil.paramError(response);
        }
        try {
            Budget query = new Budget();
            query.setBelongMonth(request.getMonth());
            query.setBelongYear(request.getYear());
            List<BudgetModel> budgets = budgetService.queryBudgetsByCondition(query);
            if (CollectionUtils.isNotEmpty(budgets)) {
                BudgetDto budgetDto;
                List<BudgetDto> budgetDtos = new ArrayList<>();
                for (BudgetModel budget : budgets) {
                    budgetDto = new BudgetDto();
                    budgetDto.setBudgetMoney(MoneyUtil.fen2Yuan(budget.getBudgetMoney()));
                    budgetDto.setTagName(budget.getTagName());
                    budgetDto.setYearMonth(budget.getBelongYear() + "-" + budget.getBelongMonth());
                    budgetDtos.add(budgetDto);
                }
                response.setBudgetList(budgetDtos);
                ResponseUtil.success(response);
            } else {
                ResponseUtil.dataNotExisting(response);
            }
        } catch (Exception e) {
            logger.error("/budget/list error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }
}
