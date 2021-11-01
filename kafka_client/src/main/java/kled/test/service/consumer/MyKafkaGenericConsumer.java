package kled.test.service.consumer;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/7/15 10:42 上午
 */
@Component
public class MyKafkaGenericConsumer {

    public static void main(String[] args) throws InterruptedException {
        new MyKafkaGenericConsumer().receive();
        Thread.sleep(100000);
    }

    public void receive() {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "43.255.229.4:9094,43.255.229.5:9094,43.255.229.6:9094");
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "kled");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, "-1"); //空闲不回收与broker的连接资源
        kafkaProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        kafkaProperties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 5 * 1000);
//        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
//        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//        kafkaProperties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");

        kafkaProperties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3 * 1000);
        kafkaProperties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 10 * 1000);

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(kafkaProperties);

//        kafkaConsumer.subscribe(Collections.singleton("flow_collector"), new ConsumerRebalanceListener() {
////        kafkaConsumer.subscribe(Collections.singleton("Test123"), new ConsumerRebalanceListener() {
//            @Override
//            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
//                System.out.println("MyKafkaGenericConsumer -> onPartitionsRevoked collection:" + collection);
//            }
//
//            @Override
//            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
//                System.out.println("MyKafkaGenericConsumer -> onPartitionsAssigned collection:" + collection);
//            }
//        });
//

        //根据时间戳查找分区的offset
//        List<PartitionInfo> partitionInfos = kafkaConsumer.partitionsFor("flow_collector");
//        List<TopicPartition> topicPartitions = partitionInfos.stream().map(x -> new TopicPartition(x.topic(), x.partition())).collect(Collectors.toList());
//        Map<TopicPartition, Long> timestampsToSearchS = topicPartitions.stream().collect(Collectors.toMap(x -> x, x -> Instant.now().toEpochMilli()));
//        Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = kafkaConsumer.offsetsForTimes(timestampsToSearchS);

        //查询主题分区的起始和终止的offset
//        kafkaConsumer.beginningOffsets(topicPartitions);
//        kafkaConsumer.endOffsets(topicPartitions);

        kafkaConsumer.subscribe(Collections.singleton("flow_collector"));
        try {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(99999);
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("MyKafkaGenericConsumer -> receive record:" + record);
            }
            //手动提交偏移量
            //kafkaConsumer.commitAsync();
            //kafkaConsumer.commitSync();
        } finally {
            kafkaConsumer.close();
        }
    }
}
