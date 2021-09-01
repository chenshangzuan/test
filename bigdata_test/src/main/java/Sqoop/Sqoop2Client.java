package Sqoop;

import org.apache.sqoop.client.SqoopClient;
import org.apache.sqoop.model.*;
import org.apache.sqoop.submission.counter.Counter;
import org.apache.sqoop.submission.counter.CounterGroup;
import org.apache.sqoop.submission.counter.Counters;
import org.apache.sqoop.validation.Status;

import java.util.UUID;

/**
 * @author Kled
 * @date 2021/8/30 6:15 下午
 */
public class Sqoop2Client {

    public static void main(String[] args) {
        String url = "http://172.16.5.56:12000/sqoop/";
        SqoopClient client = new SqoopClient(url);
        System.out.println(client);

        mysqlToHDFS(client);
    }

    public static void mysqlToHDFS(SqoopClient client) {
        //org.apache.sqoop.job.mr.SqoopOutputFormatLoadExecutor$ConsumerThread.run(SqoopOutputFormatLoadExecutor.java:259) ... 5 more Caused by:
        //org.apache.hadoop.security.AccessControlException: Permission denied: user=kled, access=WRITE, inode="/sqoop2/mysql/db/test/replication":root:supergroup:drwxr-xr-x at
        //临时解决方案：hdfs dfs -chmod -R 777 /sqoop2/mysql/db/test

        //创建一个源链接 JDBC
        MLink fromLink = client.createLink("generic-jdbc-connector");
        fromLink.setName("jdbc-link" + UUID.randomUUID().toString().substring(0, 8));
        fromLink.setCreationUser("root");
        MLinkConfig fromLinkConfig = fromLink.getConnectorLinkConfig();
        fromLinkConfig.getStringInput("linkConfig.connectionString").setValue("jdbc:mysql://172.16.5.56:3306/test?userUnicode=true&characterEncoding=UTF8&useSSL=false");
        fromLinkConfig.getStringInput("linkConfig.jdbcDriver").setValue("com.mysql.jdbc.Driver");
        fromLinkConfig.getStringInput("linkConfig.username").setValue("root");
        fromLinkConfig.getStringInput("linkConfig.password").setValue("uEXsn7NZrusBIGKe");
        fromLinkConfig.getStringInput("dialect.identifierEnclose").setValue(" ");
        Status fromStatus = client.saveLink(fromLink);
        if (fromStatus.canProceed()) {
            System.out.println("创建JDBC Link成功，ID为: " + fromLink.getPersistenceId());
        } else {
            System.out.println("创建JDBC Link失败");
        }
        //创建一个目的地链接HDFS
        //TODO:如何设置hdfs的用户，默认取当前主机名作为用户名。 无效 => System.setProperty("HADOOP_USER_NAME","root");
        MLink toLink = client.createLink("hdfs-connector");
        toLink.setName("hdfs-link" + UUID.randomUUID().toString().substring(0, 8));
        toLink.setCreationUser("root");
        MLinkConfig toLinkConfig = toLink.getConnectorLinkConfig();
        toLinkConfig.getStringInput("linkConfig.uri").setValue("hdfs://172.16.5.56:8020");
        Status toStatus = client.saveLink(toLink);
        if (toStatus.canProceed()) {
            System.out.println("创建HDFS Link成功，ID为: " + toLink.getPersistenceId());
        } else {
            System.out.println("创建HDFS Link失败");
        }

        //创建一个任务
        String fromLinkName = fromLink.getName();
        String toLinkName = toLink.getName();
        MJob job = client.createJob(fromLinkName, toLinkName);
        job.setName("mysql2hdfs" + UUID.randomUUID().toString().substring(0, 8));
        job.setCreationUser("root");
        // 设置源链接任务配置信息
        MFromConfig fromJobConfig = job.getFromJobConfig();
        fromJobConfig.getStringInput("fromJobConfig.tableName").setValue("replication");
//        fromJobConfig.getStringInput("fromJobConfig.partitionColumn").setValue("id");
        // 设置目标链接任务配置信息
        MToConfig toJobConfig = job.getToJobConfig();
        toJobConfig.getStringInput("toJobConfig.outputDirectory").setValue("/sqoop2/mysql/db/test/replication");
        toJobConfig.getBooleanInput("toJobConfig.appendMode").setValue(true);
//        toJobConfig.getEnumInput("toJobConfig.storageType").setValue("HDFS");
        toJobConfig.getEnumInput("toJobConfig.outputFormat").setValue("TEXT_FILE");
        // Job resources
        MDriverConfig driverConfig = job.getDriverConfig();
        driverConfig.getIntegerInput("throttlingConfig.numExtractors").setValue(3);
//        driverConfig.getIntegerInput("throttlingConfig.loaders").setValue(1);

        Status status = client.saveJob(job);
        if (status.canProceed()) {
            System.out.println("JOB创建成功，ID为: " + job.getPersistenceId());
        } else {
            System.out.println("JOB创建失败。");
        }

        //启动任务
        String jobName = job.getName();
        MSubmission submission = client.startJob(jobName);
        System.out.println(jobName);
        System.out.println("JOB提交状态为 : " + submission.getStatus());
        while (submission.getStatus().isRunning() && submission.getProgress() != -1) {
            System.out.println("进度 : " + String.format("%.2f %%", submission.getProgress() * 100));
            //三秒报告一次进度
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("JOB执行结束... ...");
        System.out.println("Hadoop任务ID为 :" + submission.getExternalJobId());
        Counters counters = submission.getCounters();
        if (counters != null) {
            System.out.println("计数器:");
            for (CounterGroup group : counters) {
                System.out.print("\t");
                System.out.println(group.getName());
                for (Counter counter : group) {
                    System.out.print("\t\t");
                    System.out.print(counter.getName());
                    System.out.print(": ");
                    System.out.println(counter.getValue());
                }
            }
        }
        System.out.println("MySQL通过sqoop传输数据到HDFS统计执行完毕");
    }
}
