/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.beans.PropertyDescriptor;

public class MyBean extends InstantiationAwareBeanPostProcessorAdapter implements BeanDefinitionRegistryPostProcessor, BeanFactoryPostProcessor, BeanPostProcessor, InitializingBean, DisposableBean {

    //private MySubBean mySubBean = new MySubBean();

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("BeanDefinitionRegistryPostProcessor -> postProcessBeanDefinitionRegistry \n");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("BeanFactoryPostProcessor -> postProcessBeanFactory start \n");
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean -> destroy \n");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean -> afterPropertiesSet \n");
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println("BeanPostProcessor -> postProcessBeforeInitialization \n");
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("BeanPostProcessor -> postProcessAfterInitialization \n");
        return o;
    }

    @PostConstruct
    public void postConstruct(){
        System.out.println("@PostConstruct -> postConstruct \n");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy -> preDestroy \n");
    }

    private void initMethod(){
        System.out.println("initMethod -> initMethod \n");
    }

    private void destroyMethod(){
        System.out.println("destroyMethod -> destroyMethod \n");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> aClass, String s) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor -> postProcessBeforeInstantiation \n");
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object o, String s) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor -> postProcessAfterInstantiation \n");
        return false;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, PropertyDescriptor[] propertyDescriptors, Object o, String s) throws BeansException {
        System.out.println("InstantiationAwareBeanPostProcessor -> postProcessPropertyValues \n");
        return propertyValues;
    }

    static class MySubBean{
        MySubBean() {
            System.out.println("MySubBean init");
        }
    }
}
