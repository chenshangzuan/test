/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.test.bean.MyBean1;
import spring.test.service.LeaderElectService;
import spring.test.service.LeaderElectServiceImpl;

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
