package com.tony.billing.controller;

import com.tony.billing.constants.TradeStatus;
import com.tony.billing.constants.enums.EnumHidden;
import com.tony.billing.dto.CostRecordDTO;
import com.tony.billing.dto.CostRecordDetailDTO;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.entity.PagerGrid;
import com.tony.billing.entity.TagInfo;
import com.tony.billing.entity.query.CostRecordQuery;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.request.costrecord.CostRecordDeleteRequest;
import com.tony.billing.request.costrecord.CostRecordDetailRequest;
import com.tony.billing.request.costrecord.CostRecordHideRequest;
import com.tony.billing.request.costrecord.CostRecordPageRequest;
import com.tony.billing.request.costrecord.CostRecordPutRequest;
import com.tony.billing.request.costrecord.CostRecordUpdateRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.response.costrecord.CostRecordDeleteResponse;
import com.tony.billing.response.costrecord.CostRecordDetailResponse;
import com.tony.billing.response.costrecord.CostRecordPageResponse;
import com.tony.billing.service.AlipayBillCsvConvertService;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.service.TagInfoService;
import com.tony.billing.util.MoneyUtil;
import com.tony.billing.util.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jiangwj20966 on 2017/6/2.
 */
@RestController
@RequestMapping(value = "/bootDemo")
public class CostRecordController {
    private Logger logger = LoggerFactory.getLogger(CostRecordController.class);
    @Resource
    private CostRecordService costRecordService;
    @Resource
    private AlipayBillCsvConvertService alipayBillCsvConvertService;
    @Resource
    private TagInfoService tagInfoService;

    /**
     * 获取分页数据
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cost/record/page/get")
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
    @RequestMapping(value = "/record/detail/get")
    public CostRecordDetailResponse getDetail(@ModelAttribute("request") CostRecordDetailRequest request) {
        CostRecordDetailResponse response = new CostRecordDetailResponse();
        if (StringUtils.isEmpty(request.getTradeNo())) {
            return ResponseUtil.paramError(response);
        }
        ResponseUtil.error(response);
        try {
            CostRecord record = costRecordService.findByTradeNo(request.getTradeNo(), request.getUserId());
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
        record.setUserId(request.getUserId());
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
    @RequestMapping(value = "/record/toggle/delete")
    public CostRecordDeleteResponse deleteRecord(@ModelAttribute("request") CostRecordDeleteRequest request) {
        CostRecordDeleteResponse response = new CostRecordDeleteResponse();
        try {
            if (StringUtils.isEmpty(request.getTradeNo()) || request.getNowStatus() == null) {
                return ResponseUtil.paramError(response);
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("tradeNo", request.getTradeNo());
            params.put("nowStatus", request.getNowStatus());
            params.put("isDelete", request.getNowStatus().equals(0) ? 1 : 0);
            params.put("userId", request.getUserId());
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

    @RequestMapping(value = "/toggle/record/hide")
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
            params.put("userId", request.getUserId());
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
            record.setUserId(request.getUserId());
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

    @RequestMapping("/record/csv/convert")
    public BaseResponse doConvert(@ModelAttribute("file") MultipartFile file, @ModelAttribute("request") BaseRequest request) {

        try {
            if (alipayBillCsvConvertService.convertToPOJO(file, request.getUserId())) {
                return ResponseUtil.success();
            } else {
                return ResponseUtil.error();
            }
        } catch (Exception e) {
            logger.error("backup/csv/put error", e);
            BaseResponse response = ResponseUtil.error();
            response.setMsg("文件错误请检查");
            return response;
        }
    }

    @RequestMapping("/record/backup/csv/get")
    public void backUp(HttpServletResponse response, @ModelAttribute("request") BaseRequest request) {
        CostRecord requestParam = new CostRecord();
        requestParam.setUserId(request.getUserId());
        List<CostRecord> records = costRecordService.find(requestParam);

        List<String> result = alipayBillCsvConvertService.convertPOJO2String(records);

        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + "backup" + sf.format(new Date()) + ".csv");
            OutputStream outputStream = response.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream, "GBK");
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            for (String rs : result) {
                bufferedWriter.write(rs);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
        } catch (IOException e) {
            logger.error("创建备份文件失败", e);
        }
    }

    @RequestMapping("/record/backup/csv/put")
    public BaseResponse getFromBackUp(@ModelAttribute("file") MultipartFile file, @ModelAttribute("request") BaseRequest request) {

        try {
            if (alipayBillCsvConvertService.getFromBackUp(file, request.getUserId())) {
                return ResponseUtil.success();
            } else {
                return ResponseUtil.error();
            }
        } catch (Exception e) {
            logger.error("backup/csv/put error", e);
            BaseResponse response = ResponseUtil.error();
            response.setMsg("文件错误请检查");
            return response;
        }
    }

    private String generateTradeNo(String createTime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datetime = sdf.parse(createTime);
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateCode = sf.format(datetime);

        return dateCode + String.valueOf(datetime.getTime() / 1000 % 1000000000);
    }

    private CostRecordDetailDTO formatDetailModel(CostRecord record) {
        CostRecordDetailDTO model = new CostRecordDetailDTO();
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
        model.setIsHidden(record.getIsHidden());
        return model;
    }


    private List<CostRecordDTO> formatModelList(List<CostRecordQuery> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<CostRecordDTO> models = new ArrayList<CostRecordDTO>();
            CostRecordDTO model;
            List<TagInfo> tagInfos;
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
                model.setIsHidden(entity.getIsHidden());
                tagInfos = tagInfoService.listTagInfoByTradeNo(entity.getTradeNo());
                if (!CollectionUtils.isEmpty(tagInfos)) {
                    model.setTags(new ArrayList<>());
                    for (TagInfo tagInfo : tagInfos) {
                        model.getTags().add(tagInfo.getTagName());
                    }
                }
                models.add(model);
            }
            return models;
        } else {
            return null;
        }

    }
}
