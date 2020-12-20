/**
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package kled.test.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 *
 * @author kled
 * @version $Id: WebConfig.java, v 0.1 2017年9月9日 上午10:41:35 kled Exp $
 */
//@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    //如果使用WebMvcConfigurationSupport，需配置静态资源映射
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/distv2/**").addResourceLocations("classpath:/static/distv2/");
    }
}
