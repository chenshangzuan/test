/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import kled.test.bean.MyBean1;
import kled.test.service.LeaderElectService;
import kled.test.service.LeaderElectServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    //    @Bean
    //    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
    //        return new SpringBeanFactoryPostProcessor();
    //    }
    //
    //    @Bean
    //    public InstantiationAwareBeanPostProcessor instantiationAwareBeanPostProcessor() {
    //        return new SpringInstantiationAwareBeanPostProcessor();
    //    }
    //    @Bean
    //    public BeanPostProcessor SpringBeanPostProcessor() {
    //        return new SpringBeanPostProcessor();
    //    }

    @Bean
    public LeaderElectService leaderElectService(){
        return new LeaderElectServiceImpl();
    }


//    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
//    public MyBean myBean(){
//        return new MyBean();
//    }

    @Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
    public MyBean1 myBean1(){
        return new MyBean1();
    }
}
