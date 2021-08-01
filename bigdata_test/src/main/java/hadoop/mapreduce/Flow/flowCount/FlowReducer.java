package hadoop.mapreduce.Flow.flowCount;

import com.google.common.collect.Lists;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.List;

/**
 * @author Kled
 * @date 2021/8/1 11:34 上午
 */
public class FlowReducer extends Reducer<Text, FlowCountBean, Text, FlowCountBean> {

    @Override
    protected void reduce(Text key, Iterable<FlowCountBean> values, Context context) throws IOException, InterruptedException {
        List<FlowCountBean> flowCountBeanList = Lists.newArrayList(values);
        int upFlowTotal = flowCountBeanList.stream().map(FlowCountBean::getUpFlow).reduce(Integer::sum).orElse(0);
        int downFlowTotal = flowCountBeanList.stream().map(FlowCountBean::getDownFlow).reduce(Integer::sum).orElse(0);
        int upCountFlowTotal = flowCountBeanList.stream().map(FlowCountBean::getUpCountFlow).reduce(Integer::sum).orElse(0);
        int downCountFlowTotal = flowCountBeanList.stream().map(FlowCountBean::getDownCountFlow).reduce(Integer::sum).orElse(0);
        FlowCountBean flowCountBeanOut = new FlowCountBean();
        flowCountBeanOut.setUpFlow(upFlowTotal);
        flowCountBeanOut.setDownFlow(downFlowTotal);
        flowCountBeanOut.setUpCountFlow(upCountFlowTotal);
        flowCountBeanOut.setDownCountFlow(downCountFlowTotal);

        context.write(key, flowCountBeanOut);
    }
}
