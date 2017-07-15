package com.tony.billing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.service.AdminService;
import com.tony.billing.service.AlipayBillCsvConvertService;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.util.CsvParser;
import com.tony.billing.util.RedisUtils;
import com.tony.billing.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@RestController
public class HelloWorld {

    private Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @Resource
    private AdminService adminService;
    @Resource
    private CostRecordService costRecordService;
    @Resource
    private AlipayBillCsvConvertService alipayBillCsvConvertService;

    @RequestMapping("/hello/world")
    public String hello() {
//        System.out.println(JSON.toJSONString(adminService.listAdmin()));
        return "HelloWorld";
    }

    @RequestMapping("/hello/all")
    public List<CostRecord> getLittle() {
        CostRecord record = new CostRecord();
        record.setInOutType("支出");
        List<CostRecord> records = costRecordService.find(record);
        return records.subList(0, records.size() / 10);
    }

    @RequestMapping("/hello/convert")
    public List<String> getConvertResult(@ModelAttribute("file") MultipartFile file) {
        if (file != null) {
            try {
                InputStream inputStream = file.getInputStream();
                CsvParser csvParser = new CsvParser(inputStream);
//                csvParser.readInformation();
                if (!CollectionUtils.isEmpty(csvParser.getList())) {
                    return csvParser.getList();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("/hello/convert/demo")
    public JSON doConvert(@ModelAttribute("file") MultipartFile file, @ModelAttribute("request") BaseRequest request) {
        JSONObject json = new JSONObject();
        if (alipayBillCsvConvertService.convertToPOJO(file, request.getUserId())) {
            json.put("msg", "转换成功");
        } else {
            json.put("msg", "转换失败");
        }
        return json;
    }

    @RequestMapping("/hello/redis/test")
    public BaseResponse redisTest(@ModelAttribute("request") BaseRequest request) {
        RedisUtils.set("redis-key", "asdfasdf");
        RedisUtils.del("e15151d40a87894b0ce1eb7310d4d34e1500099584");
        return ResponseUtil.success(new BaseResponse());
    }


}
