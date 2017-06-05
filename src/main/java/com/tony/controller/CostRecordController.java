package com.tony.controller;

import com.tony.entity.CostRecord;
import com.tony.entity.PagerGrid;
import com.tony.model.CostRecordDetailModel;
import com.tony.model.CostRecordModel;
import com.tony.request.CostRecordDeleteRequest;
import com.tony.request.CostRecordDetailRequest;
import com.tony.request.CostRecordPageRequest;
import com.tony.request.CostRecordPutRequest;
import com.tony.response.BaseResponse;
import com.tony.response.CostRecordDeleteResponse;
import com.tony.response.CostRecordDetailResponse;
import com.tony.response.CostRecordPageResponse;
import com.tony.service.CostRecordService;
import com.tony.util.MoneyUtil;
import com.tony.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author jiangwj20966 on 2017/6/2.
 */
@RestController
@RequestMapping(value = "/bootDemo")
public class CostRecordController {
    private Logger logger = LoggerFactory.getLogger(CostRecordController.class);
    @Resource
    private CostRecordService costRecordService;


    @RequestMapping(value = "/page/get")
    public CostRecordPageResponse getPage(@ModelAttribute("request") CostRecordPageRequest request) {
        CostRecordPageResponse response = new CostRecordPageResponse();
        try {
            CostRecord costRecord = new CostRecord();
            if (request.getIsDelete() != null) {
                costRecord.setIsDelete(request.getIsDelete());
            }
            PagerGrid<CostRecord> pagerGrid = new PagerGrid<CostRecord>(costRecord);
            if (request.getPageSize() != null && !request.getPageSize().equals(0)) {
                pagerGrid.setOffset(request.getPageSize());
            }
            pagerGrid.setPage(request.getPageNo() == null ? 0 : request.getPageNo());
            pagerGrid.setOrderBy("createTime");
            pagerGrid.setSort("desc");
            pagerGrid = costRecordService.page(pagerGrid);
//            logger.info(JSON.toJSONString(pagerGrid.getResult()));
            response.setCostRecordList(formatModelList(pagerGrid.getResult()));
            response.setCurrentAmount(calculeCurrentAmount(pagerGrid.getResult()));
            response.setPageNo(pagerGrid.getPage());
            response.setPageSize(pagerGrid.getOffset());
            response.setTotalPage(pagerGrid.getTotalPage());
            response.setTotalItem(pagerGrid.getCount());
            ResponseUtil.success(response);

        } catch (Exception e) {
            logger.error("/page/get error", e);
            ResponseUtil.sysError(response);
        }

        return response;
    }

    private String calculeCurrentAmount(List<CostRecord> result) {
        long total = 0L;
        for (CostRecord entity : result) {
            total += entity.getMoney();
        }
        return MoneyUtil.fen2Yuan(total);
    }

    @RequestMapping(value = "/detail/get")
    public CostRecordDetailResponse getDetail(@ModelAttribute("request") CostRecordDetailRequest request) {
        CostRecordDetailResponse response = new CostRecordDetailResponse();
        if (StringUtils.isEmpty(request.getTradeNo())) {
            return (CostRecordDetailResponse) ResponseUtil.paramError(response);
        }
        ResponseUtil.error(response);
        try {
            CostRecord record = costRecordService.findByTradeNo(request.getTradeNo());
            if (record != null) {
                response.setRecordDetail(formatDetailModel(record));
                ResponseUtil.success(response);
            }
        } catch (Exception e) {
            logger.error("/detail/get error", e);
        }
        return response;

    }

    @RequestMapping(value = "/delete")
    public CostRecordDeleteResponse deleteRecord(@ModelAttribute("request") CostRecordDeleteRequest request) {
        CostRecordDeleteResponse response = new CostRecordDeleteResponse();
        try {
            if (StringUtils.isEmpty(request.getTradeNo()) || request.getNowStatus() == null) {
                return (CostRecordDeleteResponse) ResponseUtil.paramError(response);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tradeNo", request.getTradeNo());
            params.put("nowStatus", request.getNowStatus());
            params.put("isDelete", request.getNowStatus().equals(0) ? 1 : 0);
            if (costRecordService.toggleDeleteStatus(params) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/delete error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    @RequestMapping(value = "/record/put")
    public BaseResponse putDetail(@ModelAttribute("request") CostRecordPutRequest request) {

        BaseResponse response = new BaseResponse();
        try {
            if (StringUtils.isEmpty(request.getCreateTime())
                    || StringUtils.isEmpty(request.getInOutType())
                    || StringUtils.isEmpty(request.getMoney())
                    || StringUtils.isEmpty(request.getTarget())) {
                return ResponseUtil.paramError(response);
            }

            CostRecord record = new CostRecord();
            record.setTradeStatus("交易成功");
            record.setTradeNo(generateTradeNo(request.getCreateTime()));
            record.setTarget(request.getTarget());
            record.setPaidTime(request.getCreateTime());
            record.setOrderType(request.getOrderType());
            record.setMoney(MoneyUtil.yuan2fen(request.getMoney()));
            record.setCreateTime(request.getCreateTime());
            record.setIsDelete(0);
            record.setOrderStatus("交易成功");
            record.setInOutType(request.getInOutType());
            record.setMemo(request.getMemo());
            record.setGoodsName(request.getMemo());
            record.setLocation(request.getLocation());
            if (costRecordService.orderPut(record) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/record/put error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    private String generateTradeNo(String createTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = sdf.parse(createTime);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCode = sf.format(datetime);

        return dateCode + String.valueOf(datetime.getTime() / 1000 % 1000000000);
    }

    private CostRecordDetailModel formatDetailModel(CostRecord record) {
        CostRecordDetailModel model = new CostRecordDetailModel();
        model.setCreateTime(record.getCreateTime());
        model.setGoodsName(record.getGoodsName());
        model.setInOutType(record.getInOutType());
        model.setIsDelete(record.getIsDelete());
        model.setLocation(record.getLocation());
        model.setMemo(record.getMemo());
        model.setMoney(MoneyUtil.fen2Yuan(record.getMoney()));
        model.setModifyTime(record.getModifyTime());
        model.setOrderNo(record.getOrderNo());
        model.setOrderStatus(record.getOrderStatus());
        model.setOrderType(record.getOrderType());
        model.setPaidTime(record.getPaidTime());
        model.setRefundMoney(MoneyUtil.fen2Yuan(record.getRefundMoney()));
        model.setServiceCost(MoneyUtil.fen2Yuan(record.getServiceCost()));
        model.setTarget(record.getTarget());
        model.setTradeNo(record.getTradeNo());
        model.setTradeStatus(record.getTradeStatus());
        return model;
    }


    private List<CostRecordModel> formatModelList(List<CostRecord> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<CostRecordModel> models = new ArrayList<CostRecordModel>();
            CostRecordModel model;
            for (CostRecord entity : list) {
                model = new CostRecordModel();
                model.setCreateTime(entity.getCreateTime());
                model.setGoodsName(entity.getGoodsName());
                model.setInOutType(entity.getInOutType());
                model.setIsDelete(entity.getIsDelete());
                model.setMoney(MoneyUtil.fen2Yuan(entity.getMoney()));
                model.setLocation(entity.getLocation());
                model.setOrderStatus(entity.getOrderStatus());
                model.setOrderType(entity.getOrderType());
                model.setTradeNo(entity.getTradeNo());
                models.add(model);
            }
            return models;
        } else {
            return null;
        }

    }
}
