/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.listener;

import org.springframework.amqp.ImmediateAcknowledgeAmqpException;
import org.springframework.amqp.ImmediateRequeueAmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MqRetryListener {

    @RabbitListener(queues = "testQ6", containerFactory = "rabbitListenerContainerFactory")
    public void test1(Message message) throws IOException {
        System.out.println("MqRetryListener -> message=" + new String(message.getBody()));
        //throw new ImmediateAcknowledgeAmqpException("aa");
        throw new ImmediateRequeueAmqpException("aa");
        //throw new RuntimeException("aa");
    }
}
