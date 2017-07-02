package com.tony;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
@MapperScan("com.tony.dao")
public class Application {
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


}
