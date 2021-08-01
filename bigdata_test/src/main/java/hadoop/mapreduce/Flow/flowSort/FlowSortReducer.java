package hadoop.mapreduce.Flow.flowSort;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/1 11:34 上午
 */
public class FlowSortReducer extends Reducer<FlowSortBean, NullWritable, FlowSortBean, NullWritable> {

    @Override
    protected void reduce(FlowSortBean flowSortBean, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(flowSortBean, NullWritable.get());
    }
}
