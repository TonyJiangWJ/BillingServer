package com.tony.billing;

import com.tony.billing.util.RSAUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author by TonyJiang on 2017/8/19.
 */
@SpringBootApplication
@MapperScan("com.tony.billing.dao.mapper")
@ComponentScan
public class TestApplication {

    @Value("${rsa.key.path}")
    private String rsaKeyPath;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public RSAUtil rsaUtil() throws Exception {
        return new RSAUtil(rsaKeyPath);
    }
}
