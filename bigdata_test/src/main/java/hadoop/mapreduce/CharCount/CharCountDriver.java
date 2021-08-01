package hadoop.mapreduce.CharCount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author Kled
 * @date 2021/6/18 3:21 下午
 */
//MRAppMaster
public class CharCountDriver extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(getConf(), "char count");
        job.setJarByClass(getClass());

        job.setInputFormatClass(TextInputFormat.class/*默认单行读取*/); //可自定义输入格式化类
        FileInputFormat.addInputPath((JobConf) job.getConfiguration(), new Path(strings[0]));
        job.setOutputFormatClass(TextOutputFormat.class/*默认单行输出*/); //可自定义输出格式化类
        FileOutputFormat.setOutputPath((JobConf) job.getConfiguration(), new Path(strings[1]));

        //Map阶段 -> MapTask
        job.setMapperClass(CharCountMapper.class);

        //Shuffle阶段
//        job.setSortComparatorClass(); //排序
        job.setPartitionerClass(CharCountPartitioner.class); //分区，默认按Key Hash
        //job.setCombinerClass(CharCountCombiner.class);    //局部合并
        //分组？

        //Reduce阶段 -> ReduceTask
        job.setReducerClass(CharCountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        //设置reduceTask的个数
        //分区文件与reduceTask的个数一致
        job.setNumReduceTasks(2);
        return job.waitForCompletion(true) ? 0 : 1;
    }

//    public static void main(String[] args) throws Exception {
//        int exitCode = ToolRunner.run(new CharCountDriver(), new String[]{args[0], args[1]});
//        System.out.println(exitCode);
//
//        //集群执行mapreduce任务，指定
//        //hadoop jar hadoop_test-1.0-SNAPSHOT.jar hadoop.mapreduce.CharCount.CharCountDriver /hadoop.mapreduce/charInput /hadoop.mapreduce/charOutput
//    }

    public static void main(String[] args) throws Exception {
        String input = "/Users/kled/git/test/bigdata_test/src/main/resources/CharCountInput.txt";
        String output = "/Users/kled/git/test/bigdata_test/src/main/resources/CharCountOutput";
        int exitCode = ToolRunner.run(new CharCountDriver(), new String[]{input, output});
        System.out.println(exitCode);

        //集群执行mapreduce任务，指定
        //hadoop jar hadoop_test-1.0-SNAPSHOT.jar hadoop.mapreduce.CharCount.CharCountDriver /hadoop.mapreduce/charInput /hadoop.mapreduce/charOutput
    }
}
