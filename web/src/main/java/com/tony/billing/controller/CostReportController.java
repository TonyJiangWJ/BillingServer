package com.tony.billing.controller;

import com.alibaba.fastjson.JSON;
import com.tony.billing.dto.ReportDTO;
import com.tony.billing.entity.ReportEntity;
import com.tony.billing.request.costreport.CostReportRequest;
import com.tony.billing.request.costreport.DailyCostReportRequest;
import com.tony.billing.response.costrecord.ReportResponse;
import com.tony.billing.service.CostReportService;
import com.tony.billing.util.BeanCopyUtil;
import com.tony.billing.util.ResponseUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author by TonyJiang on 2017/6/10.
 */
@RestController
@RequestMapping("/bootDemo")
public class CostReportController extends BaseController {

    @Resource
    private CostReportService costReportService;

    @RequestMapping(value = "/report/get")
    public ReportResponse getReport(@ModelAttribute("request") CostReportRequest request) {
        ReportResponse response = new ReportResponse();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Calendar calendar = Calendar.getInstance();
            List<String> monthList = new ArrayList<>();
            if (StringUtils.isEmpty(request.getStartMonth()) || StringUtils.isEmpty(request.getEndMonth())) {
                int ngm = -6;
                calendar.add(Calendar.MONTH, ngm);
                for (int i = 0; i < -ngm; i++) {
                    calendar.add(Calendar.MONTH, 1);
                    monthList.add(sdf.format(calendar.getTime()));
                }
            } else {
                Date startDate = sdf.parse(request.getStartMonth());
                Date endDate = sdf.parse(request.getEndMonth());
                calendar.setTime(startDate);
                Date tempDate;
                do {
                    monthList.add(sdf.format(calendar.getTime()));
                    calendar.add(Calendar.MONTH, 1);
                    tempDate = calendar.getTime();
                } while (tempDate.compareTo(endDate) <= 0);
            }
            List<ReportEntity> reportList = costReportService.getReportByDatePrefix(monthList, request.getUserId());
            if (CollectionUtils.isEmpty(reportList)) {
                ResponseUtil.dataNotExisting(response);
            } else {
                response.setReportList(BeanCopyUtil.copy(reportList, ReportDTO.class));
                ResponseUtil.success(response);
            }
        } catch (Exception e) {
            logger.error("/report/get error", e);
            ResponseUtil.sysError(response);
        }
        return response;
    }

    @RequestMapping("/daily/report/get")
    public ReportResponse getDailyCostReport(@ModelAttribute("request") DailyCostReportRequest reportRequest) {
        ReportResponse response = new ReportResponse();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isEmpty(reportRequest.getEndDate())
                || StringUtils.isEmpty(reportRequest.getStartDate())) {
            return (ReportResponse) ResponseUtil.paramError(response);
        }
        try {
            Date startDate = simpleDateFormat.parse(reportRequest.getStartDate());
            Date endDate = simpleDateFormat.parse(reportRequest.getEndDate());
            Calendar start = Calendar.getInstance();
            start.setTime(startDate);
            Calendar end = Calendar.getInstance();
            end.setTime(endDate);
            List<String> datePrefixes = new ArrayList<>();
            while (start.compareTo(end) <= 0) {
                datePrefixes.add(simpleDateFormat.format(start.getTime()));
                start.add(Calendar.DATE, 1);
            }
            List<ReportEntity> result = costReportService.getReportByDatePrefix(datePrefixes, reportRequest.getUserId());
            if (CollectionUtils.isEmpty(result)) {
                ResponseUtil.dataNotExisting(response);
            } else {
                response.setReportList(BeanCopyUtil.copy(result, ReportDTO.class));
                ResponseUtil.success(response);
            }
            return response;
        } catch (Exception e) {
            logger.error("/daily/report/get error", e);
            return (ReportResponse) ResponseUtil.sysError(response);
        }
    }


    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.MONTH, 1);
            monthList.add(sdf.format(calendar.getTime()));
        }
        System.out.println(JSON.toJSONString(monthList));
    }
}
