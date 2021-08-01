package hadoop.mapreduce.Flow.flowCount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/1 11:07 上午
 */
public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowCountBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] flowInfo = value.toString().split("\t");

        FlowCountBean flowCountBean = new FlowCountBean();
        flowCountBean.setUpFlow(Integer.parseInt(flowInfo[6]));
        flowCountBean.setDownFlow(Integer.parseInt(flowInfo[7]));
        flowCountBean.setUpCountFlow(Integer.parseInt(flowInfo[8]));
        flowCountBean.setDownCountFlow(Integer.parseInt(flowInfo[9]));
        context.write(new Text(flowInfo[1]), flowCountBean);
    }
}
