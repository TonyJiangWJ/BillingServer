package com.tony.billing.controller;

import com.alibaba.fastjson.JSON;
import com.tony.billing.entity.ReportEntity;
import com.tony.billing.dto.ReportDto;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.response.costrecord.ReportResponse;
import com.tony.billing.service.CostReportService;
import com.tony.billing.util.BeanCopyUtil;
import com.tony.billing.util.ResponseUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Author by TonyJiang on 2017/6/10.
 */
@RestController
@RequestMapping("/bootDemo")
public class CostReportController {

    @Resource
    private CostReportService costReportService;

    @RequestMapping(value = "/report/get")
    public ReportResponse getReport(@ModelAttribute("request") BaseRequest request) {
        ReportResponse response = new ReportResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        int ngm = -6;
        if (request.getUserId().equals(2L)) {
            Calendar calendarMonth2 = Calendar.getInstance();
            calendarMonth2.set(2017, Calendar.FEBRUARY, 1);
            ngm = calendarMonth2.get(Calendar.MONTH) - calendar.get(Calendar.MONTH) - 1;
        }
        calendar.add(Calendar.MONTH, ngm);
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < -ngm; i++) {
            calendar.add(Calendar.MONTH, 1);
            monthList.add(sdf.format(calendar.getTime()));
        }
        List<ReportEntity> reportList = costReportService.getMonthReportByMonth(monthList, request.getUserId());
        if (CollectionUtils.isEmpty(reportList)) {
            ResponseUtil.dataNotExisting(response);
        } else {
            response.setReportList(BeanCopyUtil.copy(reportList, ReportDto.class));
            ResponseUtil.success(response);
        }
        return response;
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
