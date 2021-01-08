/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author kled
 * @version $Id: RabbitmqDeathLetterConfig.java, v 0.1 2020年9月14日 下午3:50:12 kled Exp $
 */
@Configuration
public class RabbitmqDeathLetterConfig {

    @Bean(name = "testQ7")
    public Queue testQ7() {
        //消息传入死信队列条件
        //1.消息被拒绝（Basic.Reject/Basic.Nack）且不重新投递（requeue=false）
        //2.消息过期
        //3.队列溢出
        return QueueBuilder
                .durable("testQ7")
                .withArgument("x-dead-letter-exchange", "deathLetterQ.direct")
                .withArgument("x-dead-letter-routing-key", "deathLetterQ_direct_q")
                .withArgument("x-message-ttl", 10000) //单位ms
                .withArgument("x-max-length", 5)
                .build();
    }

    @Bean
    public DirectExchange testQ7directExchange() {
        return new DirectExchange("testQ7.direct");
    }

    @Bean
    public Binding bindingTestQ7() {
        return BindingBuilder.bind(testQ7()).to(testQ7directExchange()).with("testQ7_direct_q");
    }

    @Bean(name = "deathLetterQ")
    public Queue deathLetterQ() {
        return new Queue("deathLetterQ", true);
    }

    @Bean
    public DirectExchange deathLetterDirectExchange() {
        return new DirectExchange("deathLetterQ.direct");
    }

    @Bean
    public Binding bindingDeathLetterQ() {
        return BindingBuilder.bind(deathLetterQ()).to(deathLetterDirectExchange()).with("deathLetterQ_direct_q");
    }
}
