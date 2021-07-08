package hadoop.mapreduce;

import hadoop.mapreduce.CharCountDriver;

/**
 * @author Kled
 * @date 2021/6/18 4:18 下午
 */
public class MapReduceClient {

//    public static void main(String[] args) throws Exception {
//        System.setProperty("HADOOP_USER_NAME", "root");
//        Configuration conf = new Configuration();
//        // 指定NameNode
//        conf.set("fs.defaultFS", "hdfs://172.16.5.56:8020");
//        conf.set("hadoop.mapreduce.framework.name", "yarn");
//        conf.set("yarn.resourcemanager.address", "172.16.5.56:8032");
//        conf.set("yarn.resourcemanager.scheduler.address", "172.16.5.56:8030");
//        conf.set("hadoop.mapreduce.jobhistory.address", "172.16.5.56:10020");
//        conf.setBoolean("hadoop.mapreduce.app-submission.cross-platform", true);
//		conf.set("hadoop.mapreduce.job.jar", "C:\\Users\\liangsw\\Desktop\\wordcount.jar");
//
//        Path input = new Path("/hadoop.mapreduce/charInput.txt");
//        Path output = new Path("/hadoop.mapreduce/charOutput");
//
//        FileSystem fileSystem = FileSystem.get(new URI("hdfs://172.16.5.56:8020"), new Configuration(), "root");
//        fileSystem.delete(output, true);
//
//        CharCountDriver charCountDriver = new CharCountDriver();
//        charCountDriver.setConf(conf);
//        int exitCode = charCountDriver.run(new String[]{input.toString(), output.toString()});
//        System.out.println(exitCode);  // --> exitCode = 1
//    }
}
