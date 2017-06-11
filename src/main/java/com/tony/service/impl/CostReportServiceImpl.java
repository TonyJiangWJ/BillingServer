package com.tony.service.impl;

import com.tony.constants.EnumDeleted;
import com.tony.constants.EnumHidden;
import com.tony.constants.InOutType;
import com.tony.dao.CostReportDao;
import com.tony.entity.ReportEntity;
import com.tony.entity.SimpleReportEntity;
import com.tony.entity.query.ReportEntityQuery;
import com.tony.service.CostReportService;
import com.tony.util.MoneyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Author by TonyJiang on 2017/6/11.
 */
@Service
public class CostReportServiceImpl implements CostReportService {

    @Resource
    private CostReportDao costReportDao;

    @Override
    public List<ReportEntity> getMonthReportByMonth(List<String> months) {
        List<ReportEntity> reportList = new ArrayList<>();
        for (String month : months) {
            reportList.add(getReportInfo(month));
        }
        return reportList;
    }

    private ReportEntity getReportInfo(String month) {

        SimpleReportEntity allInfo = getAllMonthReport(month);
        SimpleReportEntity exceptDeleted = getMonthReportExceptDeleted(month);
        SimpleReportEntity exceptHidden = getMonthReportExceptHidden(month);
        SimpleReportEntity exceptDeletedAndHidden = getMonthReportExceptDeletedAndHidden(month);
        SimpleReportEntity hidden = getMonthReportHidden(month);
        SimpleReportEntity deleted = getMonthReportDeleted(month);
        SimpleReportEntity deletedAndHidden = getMonthReportDeletedAndHidden(month);
        ReportEntity report = new ReportEntity();
        report.setMonth(month);
        report.setTotalCost(MoneyUtil.fen2Yuan(allInfo.getTotalCost()));
        report.setTotalCostExceptDeleted(MoneyUtil.fen2Yuan(exceptDeleted.getTotalCost()));
        report.setTotalCostExceptHidden(MoneyUtil.fen2Yuan(exceptHidden.getTotalCost()));
        report.setTotalCostDeleted(MoneyUtil.fen2Yuan(deleted.getTotalCost()));
        report.setTotalCostHidden(MoneyUtil.fen2Yuan(hidden.getTotalCost()));
        report.setTotalCostDeletedAndHidden(MoneyUtil.fen2Yuan(deletedAndHidden.getTotalCost()));
        report.setTotalCostExceptDeletedAndHidden(MoneyUtil.fen2Yuan(exceptDeletedAndHidden.getTotalCost()));

        report.setTotalIncome(MoneyUtil.fen2Yuan(allInfo.getTotalIncome()));
        report.setTotalIncomeExceptDeleted(MoneyUtil.fen2Yuan(exceptDeleted.getTotalIncome()));
        report.setTotalIncomeExceptHidden(MoneyUtil.fen2Yuan(exceptHidden.getTotalIncome()));
        report.setTotalIncomeDeleted(MoneyUtil.fen2Yuan(deleted.getTotalIncome()));
        report.setTotalIncomeHidden(MoneyUtil.fen2Yuan(hidden.getTotalIncome()));
        report.setTotalIncomeDeletedAndHidden(MoneyUtil.fen2Yuan(deletedAndHidden.getTotalIncome()));
        report.setTotalIncomeExceptDeletedAndHidden(MoneyUtil.fen2Yuan(exceptDeletedAndHidden.getTotalIncome()));
        return report;
    }

    private SimpleReportEntity getMonthReportDeletedAndHidden(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.HIDDEN.val(), EnumDeleted.DELETED.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.HIDDEN.val(), EnumDeleted.DELETED.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }

    private SimpleReportEntity getMonthReportDeleted(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_FILTER.val(), EnumDeleted.DELETED.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_FILTER.val(), EnumDeleted.DELETED.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }

    private SimpleReportEntity getMonthReportHidden(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.HIDDEN.val(), EnumDeleted.NOT_FILTER.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.HIDDEN.val(), EnumDeleted.NOT_FILTER.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }

    private SimpleReportEntity getMonthReportExceptDeletedAndHidden(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_HIDDEN.val(), EnumDeleted.NOT_DELETED.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_HIDDEN.val(), EnumDeleted.NOT_DELETED.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }

    private SimpleReportEntity getMonthReportExceptHidden(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_HIDDEN.val(), EnumDeleted.NOT_FILTER.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_HIDDEN.val(), EnumDeleted.NOT_FILTER.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }

    private SimpleReportEntity getMonthReportExceptDeleted(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_FILTER.val(), EnumDeleted.NOT_DELETED.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_FILTER.val(), EnumDeleted.NOT_DELETED.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }

    private SimpleReportEntity getAllMonthReport(String month) {
        Long cost = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_FILTER.val(), EnumDeleted.NOT_FILTER.val(), InOutType.COST));
        Long income = costReportDao.getReportAmountByCondition(new ReportEntityQuery(month, EnumHidden.NOT_FILTER.val(), EnumDeleted.NOT_FILTER.val(), InOutType.INCOME));
        return new SimpleReportEntity(month, cost, income);
    }
}
