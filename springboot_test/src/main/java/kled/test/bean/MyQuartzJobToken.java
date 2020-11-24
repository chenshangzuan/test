/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.quartz.*;

//@DisallowConcurrentExecution
public class MyQuartzJobToken implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();
        System.out.println("Token Quartz Job!!!" + map.getString("quartz"));
    }

}
