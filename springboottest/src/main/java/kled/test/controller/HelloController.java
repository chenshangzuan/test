/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spring.test.bean.MyQuartzJobToken;
import spring.test.bean.TestJsonFormat;
import spring.test.service.HelloQuartZ;
import spring.test.service.HelloService1Impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;

@RestController
@RequestMapping(value = "/test")
public class HelloController {

    private JobDetail concurrentJob;

    private Trigger vllLockExpireTrigger;

    //@Autowired
    private HelloQuartZ helloQuartZ;

    @Autowired
    private HelloService1Impl helloSerivce1;

    @RequestMapping(value = "/httpSession", method = RequestMethod.GET)
    public String testSession(HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);
        System.out.println("httpSession=" + httpSession);
        //手动创建session, 存于内存中
        request.getSession(true).setAttribute("sessionId", "kled");
        return "success";
    }

    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String getSession(HttpServletRequest request) {
        Arrays.asList(request.getCookies()).forEach(x-> {
            System.out.println(x.getName() + ":" + x.getValue());
        });
        //测试Session域共享
        return (String) request.getSession().getAttribute("sessionId");
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String version(HttpServletRequest request,String hello) {
        System.out.println(request.getContextPath());  //web 根目录, URI-Servlet
        System.out.println(request.getServletPath());  //RequestMapper 地址
        System.out.println(request.getRequestURI());   //除了IP+PORT外的 URL
        System.out.println(request.getRequestURL());
        System.out.println(request.getQueryString());  //查询条件
        return "hello spring";
    }

    @RequestMapping(value = "/aop", method = RequestMethod.GET)
    public String testAspect() {
        System.out.println("hello spring aop");
        //throw new NullPointerException();
        return "hello spring aop";
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String version() {
        helloSerivce1.hello();
        return "hello1";
    }

    //@RequestMapping(value = "/addJob", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public void addJob() {
        try {
            JobDetail job1 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob1", "myGroup").usingJobData("quartz", "I'm job1")
                    .build();
            JobDetail job2 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob2", "myGroup").usingJobData("quartz", "I'm job2")
                    .build();
            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("myTrigger1", "myGroup").withSchedule(
                    SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).withPriority(10).build();
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("myTrigger2", "myGroup").withSchedule(
                    SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).withPriority(15).build();
            helloQuartZ.getScheduler().scheduleJob(job1, trigger1);
            helloQuartZ.getScheduler().scheduleJob(job2, trigger2);

            this.concurrentJob = job1;
            this.vllLockExpireTrigger = trigger1;
        } catch (SchedulerException e) {

        }
    }

    //@RequestMapping(value = "/removeJob", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public void removeJob() {
        try {
            helloQuartZ.getScheduler().pauseJob(vllLockExpireTrigger.getJobKey());
        } catch (SchedulerException e) {

        }
    }

    //@RequestMapping(value = "/testJsonFormat", method = RequestMethod.GET)
    public TestJsonFormat testJsonFormat(TestJsonFormat testJsonFormat) {
        System.out.println(testJsonFormat.getDateTime());
        return testJsonFormat;
    }
}
