/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.test.bean.ZookeeperFactory;
import spring.test.service.HelloCurator;

@Configuration
public class ZookeeperConfig {

    @Bean
    public ZookeeperFactory zookeeperFactory() {
        return new ZookeeperFactory();
    }

    @Bean(initMethod = "init")
    public HelloCurator helloCurator() {
        return new HelloCurator();
    }
}
