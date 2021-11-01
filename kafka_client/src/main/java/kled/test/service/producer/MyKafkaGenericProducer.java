package kled.test.service.producer;

import org.apache.kafka.clients.producer.*;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Component;

import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @author Kled
 * @date 2021/7/15 10:42 上午
 */
@Component
public class MyKafkaGenericProducer {
    public void sendMessage(){
        Properties kafkaProperties = new Properties();
        kafkaProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "172.16.5.56:9092");
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(kafkaProperties);

        kafkaProperties.put("buffer.memory", "33554432"); //32MB
        kafkaProperties.put("compress", "l4z");
        kafkaProperties.put("batch.size", "16384"); //16kb
        kafkaProperties.put("linger.ms", "100"); //超时批量发送
        kafkaProperties.put("ack", "1"); //0表示不关心写入结果, 1表示leader写入成功即可, -1表示leader的ISR列表内全部写入成功才可
        kafkaProperties.put("retries", "1");
        kafkaProperties.put("retry.backoff.ms", "300"); //时间间隔
//        kafkaProperties.put("max.in.flight.requests.per.connection", "1"); //消息严格顺序，每次发一个消息，影响吞吐量
//        kafkaProperties.put("partitioner", "kled.test.service.partitioner.MyPartitioner");

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("Test123", "kled", "Hello World");
        Future<RecordMetadata> future =  kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("MyKafkaGenericProducer -> onCompletion recordMetadata:" + recordMetadata);
            }
        });
    }
}
