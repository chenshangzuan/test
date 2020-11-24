/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class MyBeanFactory implements FactoryBean {
    private String message;

    public MyBeanFactory(String message) {
        this.message = message;
    }

    public MyBeanFactory() {
        this.message = "通过构造方法初始化实例";
    }
    @Override
    public Object getObject() throws Exception {
        // 这里并不一定要返回MyBean自身的实例，可以是其他任何对象的实例。
        //如return new Student()...
        return new MyBeanFactory("通过FactoryBean.getObject()创建实例");
    }
    @Override
    public Class<?> getObjectType() {
        return MyBeanFactory.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    public String getMessage() {
        return message;
    }
}
