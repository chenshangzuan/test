/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class MyKafkaSender {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public void sendMessage(String topic, Object object) {

        /*
         * 这里的ListenableFuture类是spring对java原生Future的扩展增强,是一个泛型接口,用于监听异步方法的回调
         * 而对于kafka send 方法返回值而言，这里的泛型所代表的实际类型就是 SendResult<K, V>,而这里K,V的泛型实际上
         * 被用于ProducerRecord<K, V> producerRecord,即生产者发送消息的key,value 类型
         */
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, object);

        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("发送消息失败:" + throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, Object> sendResult) {
                System.out.println("发送结果:" + sendResult.toString());
            }
        });
    }
}
