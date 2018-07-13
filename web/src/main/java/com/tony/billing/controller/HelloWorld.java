package com.tony.billing.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tony.billing.entity.CostRecord;
import com.tony.billing.request.BaseRequest;
import com.tony.billing.request.RealNameRequest;
import com.tony.billing.response.BaseResponse;
import com.tony.billing.service.AdminService;
import com.tony.billing.service.AlipayBillCsvConvertService;
import com.tony.billing.service.CostRecordService;
import com.tony.billing.util.CsvParser;
import com.tony.billing.util.RSAUtil;
import com.tony.billing.util.RedisUtils;
import com.tony.billing.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private RSAUtil rsaUtil;

    @RequestMapping("/hello/world")
    public String hello(@RequestParam("dd") String dd) {
//        System.out.println(JSON.toJSONString(adminService.listAdmin()));
        logger.info("dd:{}", dd);
        logger.info("enter :{}", rsaUtil.encrypt("123456"));
        return "HelloWorld" + dd;
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

    @RequestMapping("/hello/mockrealname")
    public JSONObject realNameCheck(@ModelAttribute("request") RealNameRequest realNameRequest,
                                    HttpServletRequest request) {
        JSONObject jsonObject = new JSONObject();
        JSONObject result = new JSONObject();
        String headerInfo = null;
        String appCode = "";
        if ((headerInfo = request.getHeader("Authorization")) != null) {
            appCode = headerInfo.split(" ")[1];
        }
        logger.info("requestInfo:{} appCode:{}", JSON.toJSONString(realNameRequest), appCode);
        boolean realNameResult = realNameRequest.getRealName().contains("pass");
        result.put("isok", realNameResult);
        result.put("realname", realNameRequest.getRealName());
        result.put("idcard", realNameRequest.getCardNo());
        result.put("idCardInfo", "身份信息");
        jsonObject.put("error_code", 0);
        jsonObject.put("reason", "成功");
        jsonObject.put("result", result);
        return jsonObject;
    }

    @RequestMapping("/hello/testRsa")
    public String testRsa(@RequestParam("content") String content) {
        logger.info("enter ");
        return rsaUtil.encrypt(content);
    }

    @RequestMapping("/hello/app/index")
    public String testData(@RequestParam(value = "data", required = false) String data) {
        logger.info("data:{} key:{} sign:{}", data);
        return "hello";
    }

}
