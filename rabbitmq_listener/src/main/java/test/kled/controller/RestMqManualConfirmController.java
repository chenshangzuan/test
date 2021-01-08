/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.controller;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mq")
public class RestMqManualConfirmController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/manual_confirmation")
    public String test1() throws Exception {
        CorrelationData correlation = new CorrelationData("uuid");
        rabbitTemplate.convertAndSend("test5.direct", "test5_direct_q", "manual confirmation message", correlation);
        return "success";
    }
}
