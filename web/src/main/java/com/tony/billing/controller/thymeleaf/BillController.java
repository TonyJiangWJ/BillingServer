package com.tony.billing.controller.thymeleaf;

import com.tony.billing.constants.enums.EnumHidden;
import com.tony.billing.controller.BaseController;
import com.tony.billing.dto.CostRecordDTO;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.PagerGrid;
import com.tony.billing.entity.query.CostRecordQuery;
import com.tony.billing.request.costrecord.CostRecordPageRequest;
import com.tony.billing.response.costrecord.CostRecordPageResponse;
import com.tony.billing.service.AlipayBillCsvConvertService;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.util.MoneyUtil;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * </p>
 * <li></li>
 *
 * @author jiangwj20966 2018/2/24
 */
@RequestMapping("/thymeleaf")
@Controller
public class BillController extends BaseController {



    private CostRecordService costRecordService;

    @Autowired
    public BillController(CostRecordService costRecordService) {
        this.costRecordService = costRecordService;
    }

    /**
     * 获取分页数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/bill/billList")
    public String getPage(@ModelAttribute("request") CostRecordPageRequest request, Model model) {
        CostRecordPageResponse response = new CostRecordPageResponse();
        try {
            CostRecordQuery costRecord = new CostRecordQuery();
            if (request.getIsDelete() != null) {
                costRecord.setIsDelete(request.getIsDelete());
            }
            if (StringUtils.isNotEmpty(request.getInOutType())) {
                costRecord.setInOutType(request.getInOutType());
            }
            if (StringUtils.isNotEmpty(request.getEndDate())) {
                String endDate = request.getEndDate();
                Integer value = Integer.valueOf(endDate.substring(8));
                endDate = endDate.substring(0, 8) + String.format("%02d", ++value);
                costRecord.setEndDate(endDate);
            }
            if (request.getIsHidden() != null) {
                costRecord.setIsHidden(request.getIsHidden());
            }
            if (StringUtils.isNotEmpty(request.getContent())) {
                costRecord.setContent(request.getContent());
            }
            costRecord.setStartDate(request.getStartDate());
            costRecord.setUserId(request.getUserId());
            PagerGrid<CostRecordQuery> pagerGrid = new PagerGrid<CostRecordQuery>(costRecord);
            if (request.getPageSize() != null && !request.getPageSize().equals(0)) {
                pagerGrid.setOffset(request.getPageSize());
            }
            pagerGrid.setPage(request.getPageNo() == null ? 0 : request.getPageNo());
            if (StringUtils.isNotEmpty(request.getSort())) {
                pagerGrid.setSort(request.getSort());
            } else {
                pagerGrid.setSort("desc");
            }
            if (StringUtils.isNotEmpty(request.getOrderBy())) {
                pagerGrid.setOrderBy(request.getOrderBy());
            } else {
                pagerGrid.setOrderBy("createTime");
            }

            pagerGrid = costRecordService.page(pagerGrid);
//            logger.info(JSON.toJSONString(pagerGrid.getResult()));
            response.setCostRecordList(formatModelList(pagerGrid.getResult()));
            response.setCurrentAmount(calculateCurrentAmount(pagerGrid.getResult()));
            response.setPageNo(pagerGrid.getPage());
            response.setPageSize(pagerGrid.getOffset());
            response.setTotalPage(pagerGrid.getTotalPage());
            response.setTotalItem(pagerGrid.getCount());
            model.addAttribute("response", response);
        } catch (Exception e) {
            logger.error("/page/get error", e);
            model.addAttribute("error", ResponseUtil.sysError());
        }
        model.addAttribute("request", request);
        return "/thymeleaf/bill/billList_mobile";
    }

    private String calculateCurrentAmount(List<CostRecordQuery> result) {
        long total = 0L;
        for (CostRecord entity : result) {
            total += entity.getMoney();
        }
        return MoneyUtil.fen2Yuan(total);
    }

    private List<CostRecordDTO> formatModelList(List<CostRecordQuery> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<CostRecordDTO> models = new ArrayList<CostRecordDTO>();
            CostRecordDTO model;
            for (CostRecord entity : list) {
                model = new CostRecordDTO();
                model.setCreateTime(entity.getCreateTime());
                model.setGoodsName(entity.getGoodsName());
                model.setInOutType(entity.getInOutType());
                model.setIsDelete(entity.getIsDelete());
                model.setMoney(MoneyUtil.fen2Yuan(entity.getMoney()));
                model.setLocation(entity.getLocation());
                model.setOrderStatus(entity.getOrderStatus());
                model.setOrderType(entity.getOrderType());
                model.setTradeNo(entity.getTradeNo());
                model.setTarget(entity.getTarget());
                model.setMemo(entity.getMemo());
                model.setIsHidden(EnumHidden.getHiddenEnum(entity.getIsHidden()).desc());
                models.add(model);
            }
            return models;
        } else {
            return null;
        }

    }
}
