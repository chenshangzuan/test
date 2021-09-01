package hadoop.mapreduce.Flow.flowCount;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author Kled
 * @date 2021/8/4 8:23 下午
 */
public class FlowGroup extends WritableComparator {

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        FlowCountBean flowCountBeanA = (FlowCountBean)a;
        FlowCountBean flowCountBeanB = (FlowCountBean)b;
        return super.compare(a, b);
    }
}
