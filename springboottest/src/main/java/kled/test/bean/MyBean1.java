/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class MyBean1 implements InitializingBean, BeanNameAware, DisposableBean {

    // Bean实例化(Construct) -> xxxxAware接口 -> 属性注入 -> @PostConstruct -> InitializingBean.afterPropertiesSet() -> init-method
    @Value("${spring.mail.host}")
    private String host = "";

    private String beanName = "";

    private MyBeanInner myBeanInner = new MyBeanInner();

    public MyBean1() {
        System.out.println("MyBean1 Construct-Method host=" + host);
    }

    @Override
    public void setBeanName(String s) {
        beanName = s;
        System.out.println("BeanNameAware : " + s + " host=" + host);
    }

    @PostConstruct
    public void postConstrcut(){
        System.out.println("postConstrcut  host=" + host);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean -> afterPropertiesSet host=" + host);
    }

    public void initMethod(){
        System.out.println("initMethod");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("preDestroy ");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean -> destroy");
    }


    public void destroyMethod(){
        System.out.println("destroyMethod");
    }

    class MyBeanInner{
        public MyBeanInner() {
            System.out.println("value setting");
        }
    }

}
