package kled.test.service.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
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
        kafkaProperties.put("bootstrap.servers", "172.16.5.56:9092");
        kafkaProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(kafkaProperties);

        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("Test123", "kled", "Hello World");
        Future<RecordMetadata> future =  kafkaProducer.send(producerRecord, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("MyKafkaGenericProducer -> onCompletion recordMetadata:" + recordMetadata);
            }
        });
    }
}
