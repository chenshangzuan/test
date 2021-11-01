package flink.partitioner;

import org.apache.flink.api.common.functions.Partitioner;

/**
 * @author Kled
 * @date 2021/10/27 4:57 下午
 */
public class MyFlinkPartitioner implements Partitioner<Integer> {

    @Override
    public int partition(Integer i, int partitionNum) {
        return i % partitionNum;
    }
}
