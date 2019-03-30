package com.tony.billing.service.impl;

import com.tony.billing.constants.enums.EnumMailTemplateName;
import com.tony.billing.service.EmailService;
import com.tony.billing.test.BaseServiceTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangwj20966 8/3/2018
 */
public class EmailServiceTest extends BaseServiceTest {

    @Autowired
    private EmailService emailService;

    @Test
    public void send() {
        emailService.sendSimpleMail("781027548@qq.com", "测算一下怎么发送", "测算一下");
    }

    @Test
    public void sendHtml() throws MessagingException {
        Map<String, Object> sendInfo = new HashMap<>();
        sendInfo.put("title", "您的注册验证码");
        sendInfo.put("typeDesc", "注册验证码");
        sendInfo.put("verifyCode", "987644");
        emailService.sendThymeleafMail("781027548@qq.com", "帐号注册验证码", sendInfo, EnumMailTemplateName.VERIFY_CODE_MAIL.getTemplateName());
    }
}
