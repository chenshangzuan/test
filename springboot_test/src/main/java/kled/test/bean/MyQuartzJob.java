/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.quartz.*;

@DisallowConcurrentExecution
public class MyQuartzJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();
        System.out.println("hello Quartz!!!" + map.getString("quartz"));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {

        }
        System.out.println("Quartz awake!!!");
    }

}
