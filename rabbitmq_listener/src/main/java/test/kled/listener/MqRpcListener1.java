/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.listener;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MqRpcListener1 {

    @Autowired
    private RabbitTemplate tmpReplyRabbitTemplate;

    //RPC Test
    @RabbitListener(queues = "testQ2")
    public void test1(Message message){
        System.out.println("msg = " + message);

        String corrId = message.getMessageProperties().getCorrelationId();
        tmpReplyRabbitTemplate.convertAndSend("", message.getMessageProperties().getReplyTo(), "temporary reply success", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId(corrId);
                return message;
            }
        });
    }
}
