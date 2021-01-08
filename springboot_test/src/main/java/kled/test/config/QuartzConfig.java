/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import kled.test.service.HelloQuartZ;
import org.springframework.context.annotation.Bean;

//@Configuration
public class QuartzConfig {

    @Bean(initMethod = "init")
    public HelloQuartZ helloQuartZ() {
        return new HelloQuartZ();
    }
}
