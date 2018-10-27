package com.tony.billing.test;

import com.alibaba.fastjson.JSON;
import com.tony.billing.Application;
import com.tony.billing.util.RSAUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author jiangwj20966 8/13/2018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class BaseControllerTest {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private RSAUtil rsaUtil;

    protected MockMvc mockMvc;

    protected Cookie[] cookies;

    protected MockHttpSession session;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();


        this.mockMvc.perform(post("/bootDemo/user/login")
                .param("userName", "justtest")
                .param("password", rsaUtil.encryptWithPubKey("123456"))
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(mvcResult -> {
                    session = (MockHttpSession) mvcResult.getRequest().getSession();
                    cookies = mvcResult.getResponse().getCookies();
                });
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(post("/bootDemo/page/get").param("userId", "2").session(session).cookie(cookies)).andDo(print()).andExpect(status().isOk());
        debug("test");
    }

    protected void debug(Object result) {
        logger.info("result: {}", JSON.toJSONString(result));
    }
}
