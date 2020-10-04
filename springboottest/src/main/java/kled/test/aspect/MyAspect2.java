/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Order(1)
//@Priority(1)
public class MyAspect2 {

    @Pointcut("execution(public * spring.test.controller.HelloController.testAspect(..))")
    public void pointcut1(){}

    @Before("pointcut1()")
    public void before(){
        System.out.println("before order1");
    }

    @After("pointcut1()")
    public void after(){
        System.out.println("after order1");
    }

    @AfterReturning("pointcut1()")
    public void afterReturn(){
        System.out.println("afterReturn order1");
    }

    @AfterThrowing("pointcut1()")
    public void afterThrowing(){
        System.out.println("afterThrowing order1");
    }

    @Around("pointcut1()")
    public void around(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("around before order1");
        pjp.proceed();
        System.out.println("around after order1");
    }
}
