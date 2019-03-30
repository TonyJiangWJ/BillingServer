package com.tony.billing.service.impl;

import com.google.common.base.Preconditions;
import com.tony.billing.dao.mapper.BudgetMapper;
import com.tony.billing.dao.mapper.CostRecordMapper;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.dao.mapper.base.AbstractMapper;
import com.tony.billing.dto.BudgetDTO;
import com.tony.billing.dto.BudgetReportItemDTO;
import com.tony.billing.dto.TagCostInfoDTO;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.functions.TagInfoToDtoListSupplier;
import com.tony.billing.model.BudgetReportModel;
import com.tony.billing.service.BudgetService;
import com.tony.billing.service.base.AbstractService;
import com.tony.billing.util.MoneyUtil;
import com.tony.billing.util.UserIdContainer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author by TonyJiang on 2017/7/8.
 */
@Service
public class BudgetServiceImpl extends AbstractService<Budget> implements BudgetService {

    private Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);
    @Resource
    private BudgetMapper budgetMapper;
    @Resource
    private TagInfoMapper tagInfoMapper;
    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("\\d{4}-\\d{2}");
    @Resource
    private CostRecordMapper costRecordMapper;

    @Override
    protected AbstractMapper<Budget> getMapper() {
        return budgetMapper;
    }

    @Override
    public Long insert(Budget budget) {
        Preconditions.checkState(StringUtils.isNotEmpty(budget.getBudgetName()), "");
        Preconditions.checkNotNull(budget.getBelongMonth(), "month must not be null");
        Preconditions.checkNotNull(budget.getBelongYear(), "year must not be null");
        Preconditions.checkNotNull(budget.getUserId(), "userId must not be null");
        return super.insert(budget);
    }

    @Override
    public List<BudgetDTO> queryBudgetsByCondition(Budget condition) {
        Preconditions.checkNotNull(condition.getUserId(), "userId must not be null");
        Preconditions.checkNotNull(condition.getBelongMonth(), "month must not be null");
        Preconditions.checkNotNull(condition.getBelongYear(), "year must not be null");
        List<Budget> budgets = budgetMapper.findByYearMonth(condition);
        return budgets.stream().map(
                (budget) -> {
                    BudgetDTO dto = new BudgetDTO();
                    dto.setId(budget.getId());
                    dto.setBudgetName(budget.getBudgetName());
                    dto.setBudgetMoney(MoneyUtil.fen2Yuan(budget.getBudgetMoney()));
                    dto.setYearMonth(budget.getBelongYear() + "-" + budget.getBelongMonth());
                    List<TagInfo> tagInfos = tagInfoMapper.listTagInfoByBudgetId(budget.getId(), budget.getUserId());
                    dto.setTagInfos(new TagInfoToDtoListSupplier(tagInfos).get());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    @Override
    public boolean updateBudget(Budget budget) {
        budget.setUserId(UserIdContainer.getUserId());
        return super.update(budget);
    }

    @Override
    public boolean deleteBudget(Long budgetId) {
        return super.deleteById(budgetId);
    }


    @Override
    public BudgetReportModel getBudgetReportByMonth(String monthInfo) {
        Preconditions.checkState(YEAR_MONTH_PATTERN.matcher(monthInfo).find(), "参数错误");
        BudgetReportModel reportInfo = new BudgetReportModel();
        reportInfo.setYearMonthInfo(monthInfo);
        String year = monthInfo.substring(0, 4);
        Integer month = NumberUtils.toInt(monthInfo.substring(5));
        Budget query = new Budget();
        query.setBelongYear(year);
        query.setBelongMonth(month);
        query.setUserId(UserIdContainer.getUserId());
        List<Budget> budgets = budgetMapper.findByYearMonth(query);
        if (CollectionUtils.isNotEmpty(budgets)) {
            List<Long> monthTagIds = tagInfoMapper.listTagIdsByBudgetMonth(year, month, UserIdContainer.getUserId(), null);
            List<CostRecord> noBudgetRecords = costRecordMapper.listByMonthAndExceptTagIds(monthInfo, UserIdContainer.getUserId(), monthTagIds);
            if (CollectionUtils.isNotEmpty(noBudgetRecords)) {
                reportInfo.setNoBudgetUsed(noBudgetRecords.stream().mapToLong(CostRecord::getMoney).sum());
            } else {
                logger.info("[{}] 未关联预算账单数据不存在", monthInfo);
            }
            List<CostRecord> budgetRecords = costRecordMapper.listByMonthAndTagIds(monthInfo, UserIdContainer.getUserId(), monthTagIds);
            if (CollectionUtils.isNotEmpty(budgetRecords)) {
                reportInfo.setBudgetUsed(budgetRecords.stream().mapToLong(CostRecord::getMoney).sum());
            } else {
                logger.info("[{}] 预算关联账单数据不存在", monthInfo);
            }
            budgets.forEach(b -> {
                List<TagInfo> budgetTags = tagInfoMapper.listTagInfoByBudgetId(b.getId(), UserIdContainer.getUserId());
                Long sum = 0L;
                BudgetReportItemDTO item = new BudgetReportItemDTO();
                List<CostRecord> costRecords = new ArrayList<>();
                if (CollectionUtils.isNotEmpty(budgetTags)) {
                    item.setTagInfos(budgetTags.stream().map(tag -> {
                        TagCostInfoDTO costInfoDTO = new TagCostInfoDTO();
                        List<CostRecord> tagCostRecords = costRecordMapper.listByMonthAndTagIds(monthInfo,
                                UserIdContainer.getUserId(),
                                Collections.singletonList(tag.getId()));
                        if (CollectionUtils.isNotEmpty(tagCostRecords)) {
                            costInfoDTO.setAmount(tagCostRecords.stream().mapToLong(CostRecord::getMoney).sum());
                            costRecords.addAll(tagCostRecords);
                        }
                        costInfoDTO.setTagId(tag.getId());
                        costInfoDTO.setTagName(tag.getTagName());
                        return costInfoDTO;
                    }).collect(Collectors.toList()));
                }
                item.setVersion(b.getVersion());
                item.setId(b.getId());
                item.setName(b.getBudgetName());
                item.setAmount(b.getBudgetMoney());
                if (CollectionUtils.isNotEmpty(costRecords)) {
                    sum = costRecords.stream().distinct().mapToLong(CostRecord::getMoney).sum();
                }
                item.setUsed(sum);
                item.setRemain(b.getBudgetMoney() - sum);
                reportInfo.addBudgetInfos(item);
            });
            return reportInfo.build();
        }
        return null;
    }

    @Override
    public List<BudgetReportModel> getNearlySixMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        List<BudgetReportModel> models = new ArrayList<>();
        // 获取下个月的预算数据
        calendar.add(Calendar.MONTH, 1);
        models.add(getBudgetReportByMonth(simpleDateFormat.format(calendar.getTime())));
        calendar.add(Calendar.MONTH, -1);
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.MONTH, -i);
            models.add(getBudgetReportByMonth(simpleDateFormat.format(calendar.getTime())));
        }
        models = models.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return models;
    }
}
