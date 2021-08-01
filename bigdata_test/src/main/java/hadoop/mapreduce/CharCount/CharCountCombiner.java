package hadoop.mapreduce.CharCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/7/31 10:11 下午
 * 局部合并，逻辑同Reducer
 */
public class CharCountCombiner extends Reducer<Text, IntWritable, Text, IntWritable>{

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int count = 0;
        for (IntWritable value : values) {
            count++;
        }
        context.write(key, new IntWritable(count));
    }
}
