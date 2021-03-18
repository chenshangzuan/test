/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.FlashMapManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport{
    //定义时间格式转换器
    @Bean
    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX"));
        mapper.setTimeZone(TimeZone.getTimeZone("UTC"));
        converter.setObjectMapper(mapper);

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        return converter;
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //自定义配置
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteMapNullValue, SerializerFeature.WriteDateUseDateFormat);
        converter.setFastJsonConfig(config);
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jackson2HttpMessageConverter());
        //converters.add(fastJsonHttpMessageConverter());
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //系统定义一个SimpleUrlHandlerMapping
        registry.addResourceHandler("books.xml").addResourceLocations("classpath:/");
        registry.addResourceHandler("/myResources/**").addResourceLocations("classpath:/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/");
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(){
        //自定义SimpleUrlHandlerMapping
        Map<String, String> urlMaps = Maps.newHashMap();
        urlMaps.put("/simpleUrl", "simpleUrlController");
        return new SimpleUrlHandlerMapping(urlMaps);
    }

    @Bean
    public FlashMapManager flashMapManager(){
        return new SessionFlashMapManager();
    }

    @Bean
    public SimpleMappingExceptionResolver mySimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver simpleMappingExceptionResolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("kled.test.exception.MyException", "myError");
        simpleMappingExceptionResolver.setExceptionMappings(mappings);
        return simpleMappingExceptionResolver;
    }
}
