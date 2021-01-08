/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import kled.test.bean.ConditionBean;
import kled.test.condition.MyConditionalAnnotation;
import kled.test.service.LeaderElectService;
import kled.test.service.LeaderElectServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public LeaderElectService leaderElectService(){
        return new LeaderElectServiceImpl();
    }

    @Bean
    @MyConditionalAnnotation(enable = true)
    public ConditionBean conditionBean(){
        return new ConditionBean();
    }
}
