package hadoop.mapreduce.Flow.flowPartition;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author Kled
 * @date 2021/8/1 11:07 上午
 */
public class FlowPartitionMapper extends Mapper<LongWritable, Text, Text, FlowInfoBean> {
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] flowInfo = value.toString().split("\t");

        FlowInfoBean flowInfoBean = new FlowInfoBean();
        flowInfoBean.setTs(Long.parseLong(flowInfo[0].trim()));
        flowInfoBean.setId(flowInfo[2]);
        flowInfoBean.setIp(flowInfo[3]);
        flowInfoBean.setUrl(flowInfo[4]);
        flowInfoBean.setUrlType(flowInfo[5]);
        flowInfoBean.setStatus(flowInfo[10]);
        flowInfoBean.setUpFlow(Integer.parseInt(flowInfo[6]));
        flowInfoBean.setDownFlow(Integer.parseInt(flowInfo[7]));
        flowInfoBean.setUpCountFlow(Integer.parseInt(flowInfo[8]));
        flowInfoBean.setDownCountFlow(Integer.parseInt(flowInfo[9]));
        context.write(new Text(flowInfo[1]), flowInfoBean);
    }
}
