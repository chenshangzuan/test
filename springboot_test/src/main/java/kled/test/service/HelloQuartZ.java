/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import kled.test.bean.MyQuartzJobToken;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class HelloQuartZ {
    
    private Scheduler expierScheduler;

    public void init(){
        try {
            JobDetail job1 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob1", "myGroup")
                    .usingJobData("quartz", "I'm job1").build();
            JobDetail job2 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob2", "myGroup")
                    .usingJobData("quartz", "I'm job2").build();
            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("myTrigger1", "myGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .withPriority(10).build();
            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("myTrigger2", "myGroup")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .withPriority(15).build();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(job1, trigger1);
            //scheduler.scheduleJob(job2, trigger2);
            expierScheduler = scheduler;
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public Scheduler getScheduler() {
        return expierScheduler;
    }

    public static void main(String[] args) {
        HelloQuartZ helloQuartZ = new HelloQuartZ();
        helloQuartZ.init();
    }
        
    
//    public static void main(String[] args) {
//        try {
//            JobDetail job1 = JobBuilder.newJob(MyQuartzJob.class).withIdentity("myJob1", "myGroup")
//                    .usingJobData("quartz", "hello").build();
//            JobDetail job2 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob2", "myGroup")
//                    .usingJobData("quartz", "hello").build();
//            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("myTrigger", "myGroup")
//                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
//                    .build();
//            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
//            scheduler.start();
//            scheduler.scheduleJob(job1, trigger);
//            scheduler.scheduleJob(job2, trigger);
//        } catch (SchedulerException e) {
//
//        }

}
