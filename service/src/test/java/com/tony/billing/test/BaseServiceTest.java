package com.tony.billing.test;

import com.tony.billing.TestApplication;
import com.tony.billing.dao.AdminDao;
import com.tony.billing.service.AdminService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Author by TonyJiang on 2017/8/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class BaseServiceTest {

    @Resource
    private AdminService adminService;


}
