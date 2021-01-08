/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kled
 * @version $Id: RabbitmqConfig.java, v 0.1 2020年9月14日 下午3:50:12 kled Exp $
 */
@Configuration
public class RabbitmqTransactionConfig {

    @Bean
    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory){
        return new RabbitTransactionManager(connectionFactory);
    }

    @Bean
    public RabbitTemplate transactedRabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate template =new RabbitTemplate(connectionFactory);
        template.setChannelTransacted(true);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean(name = "txQ1")
    public Queue txQ1() {
        return new Queue("txQ1", true);
    }

    @Bean
    public DirectExchange txQ1Exchange() {
        return new DirectExchange("tx1.direct");
    }

    @Bean
    public Binding bindingTestQ1() {
        return BindingBuilder.bind(txQ1()).to(txQ1Exchange()).with("tx1_direct_q");
    }

    @Bean(name = "txQ2")
    public Queue txQ2() {
        return new Queue("txQ2", true);
    }

    @Bean
    public DirectExchange txQ2Exchange() {
        return new DirectExchange("tx2.direct");
    }

    @Bean
    public Binding bindingTestQ2() {
        return BindingBuilder.bind(txQ2()).to(txQ2Exchange()).with("tx2_direct_q");
    }
}
