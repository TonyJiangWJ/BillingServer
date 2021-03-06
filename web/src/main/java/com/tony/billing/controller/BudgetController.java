package com.tony.billing.controller;

import com.tony.billing.dto.BudgetDTO;
import com.tony.billing.entity.Budget;
import com.tony.billing.model.BudgetReportModel;
import com.tony.billing.request.budget.BudgetDeleteRequest;
import com.tony.billing.request.budget.BudgetListRequest;
import com.tony.billing.request.budget.BudgetPutRequest;
import com.tony.billing.request.budget.BudgetUpdateRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.budget.BudgetListResponse;
import com.tony.billing.response.budget.BudgetOverviewResponse;
import com.tony.billing.service.BudgetService;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jiangwj20966 on 2017/7/13.
 */
@RestController
@RequestMapping("/bootDemo")
public class BudgetController extends BaseController {
    @Resource
    private BudgetService budgetService;

    @RequestMapping("/budget/put")
    public BaseResponse addBudget(@ModelAttribute("request") @Validated BudgetPutRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            Budget budget = new Budget();
            budget.setBelongMonth(request.getMonth());
            budget.setBelongYear(request.getYear());
            budget.setBudgetMoney(request.getAmount());
            budget.setUserId(request.getUserId());
            budget.setBudgetName(request.getName());
            if (budgetService.insert(budget) > 0) {
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

    @PostMapping("/budget/update")
    public BaseResponse updateBudget(@ModelAttribute("request") @Validated BudgetUpdateRequest request) {
        Budget updateInfo = new Budget();
        updateInfo.setId(request.getId());
        updateInfo.setBudgetName(request.getName());
        updateInfo.setVersion(request.getVersion());
        updateInfo.setBudgetMoney(request.getAmount());
        if (budgetService.updateBudget(updateInfo)) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error();
        }
    }

    @PostMapping("/budget/delete")
    public BaseResponse deleteBudget(@ModelAttribute("request") @Validated BudgetDeleteRequest request) {
        return budgetService.deleteBudget(request.getId()) ? ResponseUtil.success() : ResponseUtil.error();
    }

    @RequestMapping("/budget/list")
    public BudgetListResponse listBudget(@ModelAttribute("request") @Validated BudgetListRequest request) {
        BudgetListResponse response = new BudgetListResponse();
        try {
            Budget query = new Budget();
            query.setBelongMonth(request.getMonth());
            query.setBelongYear(request.getYear());
            query.setUserId(request.getUserId());
            List<BudgetDTO> budgets = budgetService.queryBudgetsByCondition(query);
            if (CollectionUtils.isNotEmpty(budgets)) {
                response.setBudgetList(budgets);
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

    @RequestMapping("/budget/report/list")
    public BudgetOverviewResponse getBudgetOverviewInfo() {
        List<BudgetReportModel> reportModels = budgetService.getNearlySixMonth();
        BudgetOverviewResponse response;
        if (CollectionUtils.isNotEmpty(reportModels)) {
            response = ResponseUtil.success(new BudgetOverviewResponse());
            response.setReportModelList(reportModels);
        } else {
            response = ResponseUtil.dataNotExisting(new BudgetOverviewResponse());
        }
        return response;
    }
}
