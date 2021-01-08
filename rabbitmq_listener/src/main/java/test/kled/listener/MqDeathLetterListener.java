/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqDeathLetterListener {

    @RabbitListener(queues = "testQ7")
    public void test1(Message message, Channel channel) throws IOException {
        System.out.println("MqDeathLetterListener -> message=" + new String(message.getBody()));
       //channel.basicReject(long,boolean); 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列。
        channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
    }
}
