/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAspect1 {

    @Pointcut("this(spring.test.service.HelloService1Impl)")
    public void pointcutOfThis(){}

    @Pointcut("target(spring.test.service.HelloService1Impl)")
    public void pointcutOfTarget(){}

    @Before("pointcutOfThis()")
    public void before1(){
        System.out.println("MyAspect1 advice of before <pointcutOfThis>");
    }

    @Before("pointcutOfTarget()")
    public void before2(){
        System.out.println("MyAspect1 advice of before <pointcutOfTarget>");
    }
}
