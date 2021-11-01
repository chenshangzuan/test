package kled.test.service.partitioner;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Kled
 * @date 2021/9/15 8:37 下午
 */
public class MyPartitioner implements Partitioner {
    private final Random random = new Random();

    @Override
    public int partition(String topic, Object key, byte[] bytes, Object value, byte[] bytes1, Cluster cluster) {
        List<PartitionInfo> partitionInfoList = cluster.availablePartitionsForTopic(topic);
        int partitionCount = partitionInfoList.size();
        int hotPartition = partitionInfoList.size() - 1;
        return "hot key".equals(key) ? hotPartition : random.nextInt(partitionCount);
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> map) {

    }
}
