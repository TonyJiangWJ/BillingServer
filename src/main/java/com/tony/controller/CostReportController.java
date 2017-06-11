package com.tony.controller;

import com.alibaba.fastjson.JSON;
import com.tony.entity.ReportEntity;
import com.tony.model.ReportModel;
import com.tony.response.ReportResponse;
import com.tony.service.CostReportService;
import com.tony.util.BeanCopyUtil;
import com.tony.util.ResponseUtil;
import org.springframework.util.CollectionUtils;
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
    public ReportResponse getReport() {
        ReportResponse response = new ReportResponse();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -6);
        List<String> monthList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            calendar.add(Calendar.MONTH, 1);
            monthList.add(sdf.format(calendar.getTime()));
        }
        List<ReportEntity> reportList = costReportService.getMonthReportByMonth(monthList);
        if (CollectionUtils.isEmpty(reportList)) {
            ResponseUtil.dataNotExisting(response);
        } else {
            response.setReportList(BeanCopyUtil.copy(reportList, ReportModel.class));
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
