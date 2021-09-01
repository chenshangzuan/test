package hadoop.mapreduce.Flow.flowCount;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
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
 * @date 2021/8/1 11:25 上午
 */
public class JobMain extends Configured implements Tool {
    public static void main(String[] args) throws Exception {
//        String input = "/Users/kled/git/test/bigdata_test/src/main/resources/flow.log";
//        String output = "/Users/kled/git/test/bigdata_test/src/main/resources/FlowOutput";
//        int exitCode = ToolRunner.run(new JobMain(), new String[]{input, output});
        int exitCode = ToolRunner.run(new JobMain(), new String[]{args[0], args[1]});
        System.out.println(exitCode);
    }

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(getConf(), "flowCount");
        job.setJarByClass(getClass());

        job.setInputFormatClass(TextInputFormat.class/*默认单行读取*/); //可自定义输入格式化类
        FileInputFormat.addInputPath((JobConf) job.getConfiguration(), new Path(args[0]));
        job.setOutputFormatClass(TextOutputFormat.class/*默认单行输出*/); //可自定义输出格式化类
        FileOutputFormat.setOutputPath((JobConf) job.getConfiguration(), new Path(args[1]));

        //Map阶段 -> MapTask
        job.setMapperClass(FlowMapper.class);

        //Shuffle分组(Reducer侧)：
        //job.setGroupingComparatorClass();

        //Reduce阶段 -> ReduceTask
        job.setReducerClass(FlowReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowCountBean.class);

        //设置reduceTask的个数
        job.setNumReduceTasks(1);
        return job.waitForCompletion(true) ? 0 : 1;
    }
}
