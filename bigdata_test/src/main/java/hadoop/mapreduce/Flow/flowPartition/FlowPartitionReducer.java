package hadoop.mapreduce.Flow.flowPartition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/1 11:34 上午
 */
public class FlowPartitionReducer extends Reducer<Text, FlowInfoBean, Text, FlowInfoBean> {

    @Override
    protected void reduce(Text key, Iterable<FlowInfoBean> values, Context context) throws IOException, InterruptedException {
        for (FlowInfoBean value : values) {
            context.write(key, value);
        }
    }
}
