/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.controller;

import org.springframework.amqp.core.Address;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import test.kled.response.PlainResponse;

@RestController
@RequestMapping("/mq")
public class RestTestController {

    @Autowired
    private RabbitTemplate tmpReplyRabbitTemplate;

    @Autowired
    private RabbitTemplate directReplyRabbitTemplate;

    @Autowired
    private RabbitTemplate fixedReplyRabbitTemplate;

    @GetMapping("/temporaryReply")
    public String test1(){
        //rabbitTemplate.convertAndSend("test2.direct", "test2_direct_q", "test");
        Object reply = tmpReplyRabbitTemplate.convertSendAndReceive("test2.direct", "test2_direct_q", "test temporary reply");
        return reply.toString();
    }

    @GetMapping("/directReply")
    public String test2(String param){
        Object reply = directReplyRabbitTemplate.convertSendAndReceive("test3.direct", "test3_direct_q", "test direct reply" + param);
        return reply.toString();
    }

    @GetMapping("/fixedReply")
    public String test3(String param){
        PlainResponse reply = fixedReplyRabbitTemplate.convertSendAndReceiveAsType("test4.direct", "test4_direct_q", "test direct reply" + param, new ParameterizedTypeReference<PlainResponse>() {
        });
        return reply.toString();
    }

    public static void main(String[] args) {
        Address address= new Address("fixedReplyQueue.direct/fixedReplyQueue_direct_q");
        System.out.println(address.getExchangeName() + " " + address.getRoutingKey());
    }
}
