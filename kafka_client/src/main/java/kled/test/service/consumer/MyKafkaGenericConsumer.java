package kled.test.service.consumer;

import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Kled
 * @date 2021/7/15 10:42 上午
 */
@Component
public class MyKafkaGenericConsumer {

    public void receive() {
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.5.56:9092");
        kafkaProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "kled");
        kafkaProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        kafkaProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        kafkaProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(kafkaProperties);

        kafkaConsumer.subscribe(Collections.singleton("Test123"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                System.out.println("MyKafkaGenericConsumer -> onPartitionsRevoked collection:" + collection);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                System.out.println("MyKafkaGenericConsumer -> onPartitionsAssigned collection:" + collection);
            }
        });


        try {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(500);
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
