package hadoop;

import org.apache.hadoop.conf.Configuration;

/**
 * @author Kled
 * @date 2021/6/18 11:25 上午
 */
public class HadoopUtils {
    public static Configuration initConf() {

        Configuration conf = new Configuration();

        // 指定NameNode
        conf.set("fs.defaultFS", "hadoop.hdfs://172.16.5.56:8020");
//        指定Yarn
//        conf.set("hadoop.mapreduce.framework.name", "yarn");
//        指定resourcemanager
//        conf.set("yarn.resourcemanager.address", "172.16.5.56:8032");
//        指定资源分配器
//        conf.set("yarn.resourcemanager.scheduler.address", "172.16.5.56:8030");
//        指定historyserver
//        conf.set("hadoop.mapreduce.jobhistory.address", "172.16.5.56:10020");
//        配置跨平台提交任务
//        conf.setBoolean("hadoop.mapreduce.app-submission.cross-platform", true);
//        设置Jar路径
//		  conf.set("hadoop.mapreduce.job.jar", "C:\\Users\\liangsw\\Desktop\\wordcount.jar");
        return conf;
    }
}
