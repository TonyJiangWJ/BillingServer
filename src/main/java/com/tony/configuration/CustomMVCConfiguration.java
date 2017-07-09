package com.tony.configuration;

import com.tony.interceptors.InterceptorDemo;
import com.tony.interceptors.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Author by TonyJiang on 2017/6/3.
 */
@Configuration
public class CustomMVCConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private InterceptorDemo interceptorDemo;
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptorDemo)
                .excludePathPatterns("/bootDemo/user/login*")
                .excludePathPatterns("/bootDemo/user/register/put*")
                .addPathPatterns("/bootDemo/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/bootDemo/user/login*")
                .addPathPatterns("/bootDemo/user/register/put*");
        super.addInterceptors(registry);
    }
}
