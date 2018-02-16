package com.tony.billing.test;

import com.alibaba.fastjson.JSON;
import com.tony.billing.TestApplication;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author by TonyJiang on 2017/8/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class BaseServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected void debug(Object result) {
        logger.info("result: {}", JSON.toJSONString(result));
    }

}
