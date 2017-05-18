package com.tony.controller;

import com.alibaba.fastjson.JSON;
import com.tony.entity.Admin;
import com.tony.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@RestController
public class HelloWorld {

    private Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @Resource
    private AdminService adminService;


    @ResponseBody
    @RequestMapping("/hello/world")
    public Admin hello() {
//        System.out.println(JSON.toJSONString(adminService.listAdmin()));
        List<Admin> adminList = adminService.listAdmin();
        logger.info("result:{}", JSON.toJSONString(adminList));
        return adminList.get(0);
    }
}
