/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import kled.test.bean.ZookeeperFactory;
import kled.test.service.HelloCurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
