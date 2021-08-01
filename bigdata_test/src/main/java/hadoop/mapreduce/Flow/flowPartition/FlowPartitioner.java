package hadoop.mapreduce.Flow.flowPartition;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author Kled
 * @date 2021/8/1 4:08 下午
 */
public class FlowPartitioner extends Partitioner<Text, FlowInfoBean> {
    @Override
    public int getPartition(Text key, FlowInfoBean value, int numPartitions) {
        if(key.toString().startsWith("135")){
            return 0;
        }
        if(key.toString().startsWith("136")){
            return 1;
        }
        if(key.toString().startsWith("137")){
            return 2;
        }
        return 3;
    }
}
