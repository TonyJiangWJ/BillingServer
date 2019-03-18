package com.tony.billing.test;

import com.tony.billing.TestApplication;
import com.tony.billing.util.UserIdContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 不带事务的测试基类
 *
 * @author jiangwenjie 2019-02-02
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class BaseServiceTestNoTransaction extends AbstractJUnit4SpringContextTests implements CommonTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Before
    public void setCommonUp() {
        UserIdContainer.setUserId(2L);
    }

    @After
    public void afterTest() {
        UserIdContainer.removeUserId();
    }
}
