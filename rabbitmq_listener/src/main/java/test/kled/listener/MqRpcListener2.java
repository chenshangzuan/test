/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.listener;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqRpcListener2 {

    @Autowired
    private RabbitTemplate directReplyRabbitTemplate;

    //RPC Test
    @RabbitListener(queues = "testQ3")
    public void test1(Message message){
        System.out.println("msg = " + message);

        String corrId = message.getMessageProperties().getCorrelationId();
        directReplyRabbitTemplate.convertAndSend("", message.getMessageProperties().getReplyTo(), new String(message.getBody()), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId(corrId);
                return message;
            }
        });
    }
}
