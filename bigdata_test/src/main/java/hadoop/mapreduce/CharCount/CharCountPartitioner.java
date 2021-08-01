package hadoop.mapreduce.CharCount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author Kled
 * @date 2021/7/31 8:25 下午
 */
public class CharCountPartitioner extends Partitioner<Text, IntWritable> {
    @Override
    public int getPartition(Text k, IntWritable v, int numPartitions) {
        //numPartitions对应reduceTaskNum
//        System.out.println("numPartitions:" + numPartitions);
        //如何字符在a-n，放第一个分区，其余放第二个分区
        if(k.toString().charAt(0) >= 'a' && k.toString().charAt(0) <= 'n'){
            return 0;
        }
        return 1;
    }

}
