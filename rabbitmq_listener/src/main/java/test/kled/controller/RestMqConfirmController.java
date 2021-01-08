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
public class RestMqConfirmController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/confirmation1")
    public String test1() throws Exception {
        rabbitTemplate.convertAndSend("tx1.direct", "tx1_direct_q", "confirmation message1");
        return "success";
    }

    @GetMapping("/confirmation2")
    public String test2() throws Exception {
        //无法发送至交换机，触发confirm call back ack=false
        rabbitTemplate.convertAndSend("error exchange", "tx1_direct_q", "confirmation message2");

        //无法路由至队列，触发return back
        rabbitTemplate.convertAndSend("tx1.direct", "error router key", "confirmation message2");
        return "success";
    }
}
