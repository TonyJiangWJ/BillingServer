package com.tony.billing.test;

import com.tony.billing.TestApplication;
import com.tony.billing.util.UserIdContainer;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author by TonyJiang on 2017/8/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class BaseServiceTest extends AbstractTransactionalJUnit4SpringContextTests implements CommonTest {

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
