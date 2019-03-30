package com.tony.billing.service.impl;

import com.tony.billing.dao.mapper.BudgetMapper;
import com.tony.billing.dao.mapper.CostRecordMapper;
import com.tony.billing.dao.mapper.TagInfoMapper;
import com.tony.billing.entity.Budget;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.service.BudgetService;
import com.tony.billing.test.BaseServiceTestNoTransaction;
import com.tony.billing.util.UserIdContainer;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author jiangwenjie 2019-03-18
 */
public class BudgetServiceImplTest extends BaseServiceTestNoTransaction {

    @Resource
    private BudgetService budgetService;


    @Before
    public void setUp() throws Exception {
        UserIdContainer.setUserId(2L);
    }

    @After
    public void after() {
        UserIdContainer.removeUserId();
    }

    @Test
    public void saveBudget() {
        Budget budget = new Budget();
        budget.setUserId(UserIdContainer.getUserId());
        budget.setBudgetName("测试预算4");
        budget.setBelongYear("2019");
        budget.setBelongMonth(3);
        budget.setBudgetMoney(100000L);
        budgetService.insert(budget);
    }

    @Test
    public void queryBudgetsByCondition() {
        debugInfo(budgetService.queryBudgetsByCondition(Stream.generate(() -> {
            Budget budget = new Budget();
            budget.setBelongYear("2019");
            budget.setBelongMonth(3);
            budget.setUserId(UserIdContainer.getUserId());
            return budget;
        }).findFirst().get()));
    }

    @Test
    public void updateBudget() {
        Budget budget = new Budget();
        budget.setUserId(UserIdContainer.getUserId());
        budget.setBudgetName("测试预算4-change");
        budget.setBelongYear("2019");
        budget.setBelongMonth(3);
        budget.setBudgetMoney(120000L);
        budget.setId(4L);
        budget.setVersion(0);
        debugInfo(budgetService.updateBudget(budget));
    }

    @Test
    public void deleteBudget() {
    }

    @Test
    public void getById() {
        debugInfo(budgetService.getById(1L));
    }

    @Resource
    private BudgetMapper budgetMapper;
    @Resource
    private CostRecordMapper costRecordMapper;
    @Resource
    private TagInfoMapper tagInfoMapper;

    @Test
    public void testGroupBudgets() {
        Budget budget = new Budget();
        budget.setUserId(UserIdContainer.getUserId());
        List<Budget> budgetList = budgetMapper.list(budget);
        Map<String, List<Budget>> budgetMap = budgetList.stream().collect(Collectors.groupingBy(budgetInfo ->
                String.format("%s-%02d", budgetInfo.getBelongYear(), budgetInfo.getBelongMonth())
        ));
//        debugInfo(budgetMap);
//        tagInfoMapper.listTagInfoByBudgetId()
        List<BudgetReportInfo> reportInfos = budgetMap.entrySet().stream().map(entry -> {
            BudgetReportInfo reportInfo = new BudgetReportInfo();
            reportInfo.setMonthInfo(entry.getKey());
            List<Budget> budgets = entry.getValue();
            String monthInfo = entry.getKey();
            String year = monthInfo.substring(0, 4);
            Integer month = NumberUtils.toInt(monthInfo.substring(5));
            List<Long> monthTagIds = tagInfoMapper.listTagIdsByBudgetMonth(year, month, UserIdContainer.getUserId(),null);
            List<CostRecord> noBudgetRecords = costRecordMapper.listByMonthAndExceptTagIds(monthInfo, UserIdContainer.getUserId(), monthTagIds);
            if (CollectionUtils.isNotEmpty(noBudgetRecords)) {
                reportInfo.setNoBudgetUsed(noBudgetRecords.stream().mapToLong(CostRecord::getMoney).sum());
            }
            List<CostRecord> budgetRecords = costRecordMapper.listByMonthAndTagIds(monthInfo, UserIdContainer.getUserId(), monthTagIds);
            if (CollectionUtils.isNotEmpty(budgetRecords)) {
                reportInfo.setBudgetUsed(budgetRecords.stream().mapToLong(CostRecord::getMoney).sum());
            }
            budgets.forEach(b -> {
                List<TagInfo> budgetTags = tagInfoMapper.listTagInfoByBudgetId(b.getId(), UserIdContainer.getUserId());
                List<Long> tagIds = budgetTags.stream().map(TagInfo::getId).collect(Collectors.toList());
                Long sum = 0L;
                if (CollectionUtils.isNotEmpty(tagIds)) {
                    List<CostRecord> costRecords = costRecordMapper.listByMonthAndTagIds(monthInfo,
                            UserIdContainer.getUserId(),
                            tagIds);
                    sum = costRecords.stream().mapToLong(CostRecord::getMoney).sum();
                } else {
                    logger.error("[{}] 关联标签不存在", b.getBudgetName());
                }

                BudgetItemInfo item = new BudgetItemInfo();
                item.setId(b.getId());
                item.setName(b.getBudgetName());
                item.setAmount(b.getBudgetMoney());
                item.setUsed(sum);
                item.setRemain(b.getBudgetMoney() - sum);
                item.setTagNames(budgetTags.stream().map(TagInfo::getTagName).collect(Collectors.joining(",")));
                reportInfo.addBudgetInfos(item);
            });
            return reportInfo.build();
        }).collect(Collectors.toList());

        debugInfo(reportInfos);
    }

    private class BudgetReportInfo {

        public BudgetReportInfo() {
            this.budgetUsed = this.noBudgetUsed = 0L;
            this.remain = this.totalAmount = 0L;
            budgetInfos = new ArrayList<>();
        }

        private String monthInfo;
        private Long totalAmount;
        private Long budgetUsed;
        private Long remain;
        private Long noBudgetUsed;
        private List<BudgetItemInfo> budgetInfos;

        public String getMonthInfo() {
            return monthInfo;
        }

        public void setMonthInfo(String monthInfo) {
            this.monthInfo = monthInfo;
        }

        public Long getTotalAmount() {
            return totalAmount;
        }

        public void setTotalAmount(Long totalAmount) {
            this.totalAmount = totalAmount;
        }

        public Long getBudgetUsed() {
            return budgetUsed;
        }

        public void setBudgetUsed(Long budgetUsed) {
            this.budgetUsed = budgetUsed;
        }

        public Long getNoBudgetUsed() {
            return noBudgetUsed;
        }

        public void setNoBudgetUsed(Long noBudgetUsed) {
            this.noBudgetUsed = noBudgetUsed;
        }

        public Long getRemain() {
            return remain;
        }

        public void setRemain(Long remain) {
            this.remain = remain;
        }

        public List<BudgetItemInfo> getBudgetInfos() {
            return budgetInfos;
        }

        public void setBudgetInfos(List<BudgetItemInfo> budgetInfos) {
            this.budgetInfos = budgetInfos;
        }

        public boolean addBudgetInfos(BudgetItemInfo itemInfo) {
            return this.budgetInfos.add(itemInfo);
        }

        public BudgetReportInfo build() {
            if (CollectionUtils.isNotEmpty(this.budgetInfos)) {
                budgetInfos.forEach(budget -> this.totalAmount += budget.getAmount());
            }
            this.remain = this.totalAmount - this.budgetUsed - this.noBudgetUsed;
            return this;
        }
    }

    private class BudgetItemInfo {
        private Long id;
        private String name;
        private Long amount;
        private Long used;
        private Long remain;

        private String tagNames;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Long getAmount() {
            return amount;
        }

        public void setAmount(Long amount) {
            this.amount = amount;
        }

        public Long getUsed() {
            return used;
        }

        public void setUsed(Long used) {
            this.used = used;
        }

        public Long getRemain() {
            return remain;
        }

        public void setRemain(Long remain) {
            this.remain = remain;
        }

        public String getTagNames() {
            return tagNames;
        }

        public void setTagNames(String tagNames) {
            this.tagNames = tagNames;
        }
    }
}