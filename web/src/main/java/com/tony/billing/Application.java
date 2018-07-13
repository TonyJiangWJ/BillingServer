package com.tony.billing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.billing.util.AuthUtil;
import com.tony.billing.util.RSAUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author by TonyJiang on 2017/5/18.
 */
@SpringBootApplication
@ComponentScan
@ServletComponentScan
@MapperScan("com.tony.billing.dao.mapper")
public class Application {

    @Value("${rsa.key.path}")
    private String rsaKeyPath;
    @Value("${jwt.secret.key:springboot}")
    private String jwtSecretKey;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Bean
    public ObjectMapper jsonMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // null 不输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        null 输出空字符串
//        objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
//                jgen.writeNullField("");
//            }
//        });
        return objectMapper;
    }

    @Bean
    public RSAUtil rsaUtil() throws Exception {
        return new RSAUtil(rsaKeyPath);
    }

    @Bean
    public AuthUtil authUtil() {
        return new AuthUtil(new AuthUtil.JavaWebToken(jwtSecretKey));
    }
}
