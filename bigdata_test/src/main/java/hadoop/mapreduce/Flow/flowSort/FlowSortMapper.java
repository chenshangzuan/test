package hadoop.mapreduce.Flow.flowSort;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/1 11:07 上午
 */
public class FlowSortMapper extends Mapper<LongWritable, Text, FlowSortBean, NullWritable> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] flowInfo = value.toString().split("\t");

        FlowSortBean flowSortBean = new FlowSortBean();
        flowSortBean.setPhoneNum(flowInfo[0]);
        flowSortBean.setUpFlow(Integer.parseInt(flowInfo[1]));
        flowSortBean.setDownFlow(Integer.parseInt(flowInfo[2]));
        flowSortBean.setUpCountFlow(Integer.parseInt(flowInfo[3]));
        flowSortBean.setDownCountFlow(Integer.parseInt(flowInfo[4]));
        context.write(flowSortBean, NullWritable.get());
    }
}
