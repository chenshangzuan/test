package hadoop.mapreduce;

import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author Kled
 * @date 2021/6/18 3:21 下午
 */
public class CharCountDriver extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance(getConf(), "char count");
        job.setJarByClass(getClass());

        FileInputFormat.addInputPath((JobConf) job.getConfiguration(), new Path(strings[0]));
        FileOutputFormat.setOutputPath((JobConf) job.getConfiguration(), new Path(strings[1]));

        job.setMapperClass(CharCountMapper.class);
        job.setReducerClass(CharCountReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        return job.waitForCompletion(true) ? 0 : 1;
    }

    public static void main(String[] args) throws Exception {
//        String input = "/Users/kled/git/test/hadoop_test/src/main/resources/CharCountInput.txt";
//        String output = "/Users/kled/git/test/hadoop_test/src/main/resources/CharCountOutput";
        int exitCode = ToolRunner.run(new CharCountDriver(), new String[]{args[0], args[1]});
        System.out.println(exitCode);

        //集群执行mapreduce任务，指定
        //hadoop jar hadoop_test-1.0-SNAPSHOT.jar hadoop.mapreduce.CharCountDriver /hadoop.mapreduce/charInput /hadoop.mapreduce/charOutput
    }
}
