/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Value;

public class HelloSpringBean implements BeanNameAware, BeanFactoryAware, InitializingBean, DisposableBean {

    @Value("${lock.expire.msec}")
    private int lockExpire;
    @Value("hello")
    private String str = "hi";//属性在类加载时初始化,spring属性注入在构造函数之后

    private InnerClass innerClass = new HelloSpringBean.InnerClass();

    /**创建BeanPostProcessor类***/

    /**调用构造方法前，ProcessBeforeInstantiation***/

    public HelloSpringBean() {
        System.out.println("method[construct] start" + lockExpire + str);
    }

    /**调用构造方法后，postProcessProtertyValues 准备属性参数***/

    /**开始注入属性***/
    @Override
    public void setBeanName(String name) {
        //bean 获取自己在spring内的name
        System.out.println("my name is " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        //bean 获取自己在的beanFactory
        System.out.println("my bean factory is " + beanFactory);
    }

    /**调用init-method方法前，postProcessBeforeInitialization***/

    @Override
    public void afterPropertiesSet() throws Exception {
        //属性注入之后
        System.out.println("after properties init");
    }

    public void init() {
        //init-method
        System.out.println("method[init] start" + lockExpire + str);
    }

    /**调用init-method方法前，postProcessAfterInitialization***/

    @Override
    public void destroy() throws Exception {
        //destroy-method 之前
        System.out.println("my bean is ready to be destroied");
    }

    public void doDestroy() {
        //destroy-method
        System.out.println("my bean is being destroied");
    }

    private static class InnerClass {
        public InnerClass() {
            System.out.println("InnerClass init");
        }
    }
}
