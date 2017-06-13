package com.tony.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tony.constants.EnumHidden;
import com.tony.constants.TradeStatus;
import com.tony.entity.CostRecord;
import com.tony.entity.PagerGrid;
import com.tony.entity.query.CostRecordQuery;
import com.tony.model.CostRecordDetailModel;
import com.tony.model.CostRecordModel;
import com.tony.request.*;
import com.tony.response.BaseResponse;
import com.tony.response.CostRecordDeleteResponse;
import com.tony.response.CostRecordDetailResponse;
import com.tony.response.CostRecordPageResponse;
import com.tony.service.AlipayBillCsvConvertService;
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
import org.springframework.web.multipart.MultipartFile;

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
    @Resource
    private AlipayBillCsvConvertService alipayBillCsvConvertService;

    /**
     * 获取分页数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/page/get")
    public CostRecordPageResponse getPage(@ModelAttribute("request") CostRecordPageRequest request) {
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
            if(StringUtils.isNotEmpty(request.getContent())){
                costRecord.setContent(request.getContent());
            }
            costRecord.setStartDate(request.getStartDate());
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
            ResponseUtil.success(response);

        } catch (Exception e) {
            logger.error("/page/get error", e);
            ResponseUtil.sysError(response);
        }

        return response;
    }

    private String calculateCurrentAmount(List<CostRecordQuery> result) {
        long total = 0L;
        for (CostRecord entity : result) {
            total += entity.getMoney();
        }
        return MoneyUtil.fen2Yuan(total);
    }

    /**
     * 获取详情
     *
     * @param request
     * @return
     */
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

    /**
     * 修改消费记录
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/record/update")
    public BaseResponse updateRecord(@ModelAttribute("request") CostRecordUpdateRequest request) {
        BaseResponse response = new BaseResponse();
        if (StringUtils.isEmpty(request.getTradeNo())) {
            return ResponseUtil.paramError(response);
        }
        CostRecord record = new CostRecord();
        record.setLocation(request.getLocation());
        record.setGoodsName(request.getGoodsName());
        record.setMemo(request.getMemo());
        record.setTradeNo(request.getTradeNo());
        if (costRecordService.updateByTradeNo(record) > 0) {
            return ResponseUtil.success(response);
        }
        return ResponseUtil.error(response);
    }

    /**
     * 标记删除
     *
     * @param request
     * @return
     */
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

    @RequestMapping(value = "/toggle/hide")
    public BaseResponse toggleHiddenStatus(@ModelAttribute("request") CostRecordHideRequest request) {
        BaseResponse response = new BaseResponse();
        try {
            if (StringUtils.isEmpty(request.getNowStatus()) || StringUtils.isEmpty(request.getTradeNo())) {
                return ResponseUtil.paramError(response);
            }
            Map<String, Object> params = new HashMap<>();
            params.put("tradeNo", request.getTradeNo());
            params.put("nowStatus", EnumHidden.getHiddenEnum(request.getNowStatus()).val());
            params.put("isHidden",
                    EnumHidden.getHiddenEnum(request.getNowStatus()).val().equals(EnumHidden.HIDDEN.val())
                            ? EnumHidden.NOT_HIDDEN.val() : EnumHidden.HIDDEN.val());
            if (costRecordService.toggleHideStatus(params) > 0) {
                ResponseUtil.success(response);
            } else {
                ResponseUtil.error(response);
            }
        } catch (Exception e) {
            logger.error("/toggle/hide error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    /**
     * 添加消费记录
     *
     * @param request
     * @return
     */
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
            record.setTradeStatus(TradeStatus.TRADE_SUCCESS);
            record.setTradeNo(generateTradeNo(request.getCreateTime()));
            record.setTarget(request.getTarget());
            record.setPaidTime(request.getCreateTime());
            record.setOrderType(request.getOrderType());
            record.setMoney(MoneyUtil.yuan2fen(request.getMoney()));
            record.setCreateTime(request.getCreateTime());
            record.setIsDelete(0);
            record.setOrderStatus(TradeStatus.TRADE_SUCCESS);
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
    @RequestMapping("/csv/convert")
    public JSON doConvert(@ModelAttribute("file") MultipartFile file) {
        JSONObject json = new JSONObject();
        if (alipayBillCsvConvertService.convertToPOJO(file)) {
            json.put("msg", "转换成功");
        } else {
            json.put("msg", "转换失败");
        }
        return json;
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
        model.setIsHidden(EnumHidden.getHiddenEnum(record.getIsHidden()).desc());
        return model;
    }


    private List<CostRecordModel> formatModelList(List<CostRecordQuery> list) {
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
