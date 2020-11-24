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
import test.kled.response.PlainResponse;

@Component
public class TestListener4 {

    @Autowired
    private RabbitTemplate fixedReplyRabbitTemplate;

    //RPC Test
    @RabbitListener(queues = "testQ4")
    public void test1(Message message){
        System.out.println("msg = " + message);

        PlainResponse plainResponse = new PlainResponse();
        plainResponse.setSuccess(true);
        plainResponse.setMsg(new String(message.getBody()));

        String corrId = message.getMessageProperties().getCorrelationId();
        fixedReplyRabbitTemplate.convertAndSend("", message.getMessageProperties().getReplyTo(), plainResponse, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setCorrelationId(corrId);
                return message;
            }
        });
    }
}
