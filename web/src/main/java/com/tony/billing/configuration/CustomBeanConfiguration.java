package com.tony.billing.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tony.billing.util.AuthUtil;
import com.tony.billing.util.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangwj20966 8/6/2018
 */
@Configuration
public class CustomBeanConfiguration {

    @Value("${rsa.key.path}")
    private String rsaKeyPath;
    @Value("${jwt.secret.key:springboot}")
    private String jwtSecretKey;

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
