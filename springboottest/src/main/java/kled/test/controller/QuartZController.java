package kled.test.controller;

import kled.test.service.HelloQuartZ;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Kled
 * @version: QuartZController.java, v0.1 2020-11-13 11:41 Kled
 */
@RestController
@RequestMapping("/test")
public class QuartZController {

    private JobDetail concurrentJob;

    private Trigger vllLockExpireTrigger;

    //@Autowired
    private HelloQuartZ helloQuartZ;

    //@RequestMapping(value = "/addJob", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
//    public void addJob() {
//        try {
//            JobDetail job1 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob1", "myGroup").usingJobData("quartz", "I'm job1")
//                    .build();
//            JobDetail job2 = JobBuilder.newJob(MyQuartzJobToken.class).withIdentity("myJob2", "myGroup").usingJobData("quartz", "I'm job2")
//                    .build();
//            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity("myTrigger1", "myGroup").withSchedule(
//                    SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).withPriority(10).build();
//            Trigger trigger2 = TriggerBuilder.newTrigger().withIdentity("myTrigger2", "myGroup").withSchedule(
//                    SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever()).withPriority(15).build();
//            helloQuartZ.getScheduler().scheduleJob(job1, trigger1);
//            helloQuartZ.getScheduler().scheduleJob(job2, trigger2);
//
//            this.concurrentJob = job1;
//            this.vllLockExpireTrigger = trigger1;
//        } catch (SchedulerException e) {
//
//        }
//    }

    //@RequestMapping(value = "/removeJob", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
//    public void removeJob() {
//        try {
//            helloQuartZ.getScheduler().pauseJob(vllLockExpireTrigger.getJobKey());
//        } catch (SchedulerException e) {
//
//        }
//    }

}
