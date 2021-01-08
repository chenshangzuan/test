/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class RestMqTxController {

    @Autowired
    private RabbitTemplate transactedRabbitTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/msg")
    public String test1() throws Exception {
        rabbitTemplate.convertAndSend("tx1.direct", "tx1_direct_q", "tx1 message");
        return "success";
    }

    @GetMapping("/tx")
    //spring.rabbitmq.publisher-confirms=false 必须为false
    //不建议使用，生产者生产消息的吞吐量和性能都会大大降低。
    @Transactional(rollbackFor = Exception.class, transactionManager = "rabbitTransactionManager")
    public String test2() throws Exception {
        //多条消息会同时自动回滚
        transactedRabbitTemplate.convertAndSend("tx1.direct", "tx1_direct_q", "tx1 message");

        transactedRabbitTemplate.convertAndSend("tx2.direct", "tx2_direct_q", "tx2 message");
        throw new Exception();
    }
}
