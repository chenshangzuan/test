/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author kled
 * @version $Id: RabbitmqConfig.java, v 0.1 2020年9月14日 下午3:50:12 kled Exp $
 */
@Configuration
public class RabbitmqRpcConfig {

    @Autowired
    private ConnectionFactory connectionFactory;


    @Bean(name = "fixedReplyQueue")
    public Queue fixedReplyQueue() {
        return new Queue("fixedReplyQueue", true);
    }

    @Bean
    public RabbitTemplate tmpReplyRabbitTemplate(){
        RabbitTemplate template =new RabbitTemplate(connectionFactory);
        template.setUseTemporaryReplyQueues(true);
        return template;
    }

    @Bean
    public RabbitTemplate directReplyRabbitTemplate(){
        RabbitTemplate template =new RabbitTemplate(connectionFactory);
        template.setUseTemporaryReplyQueues(false);
        template.setReplyAddress("amq.rabbitmq.reply-to"); //虚拟内部队列
        //direct-reply-to 不支持reply queue name理想设定
        //template.expectedQueueNames();
        return template;
    }

    @Bean
    public RabbitTemplate fixedReplyRabbitTemplate(){
        RabbitTemplate template =new RabbitTemplate(connectionFactory);
        template.setUseTemporaryReplyQueues(false);
        template.setReplyAddress("fixedReplyQueue");
        template.expectedQueueNames();
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer fixedSimpleMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //这一步非常重要，固定队列模式要，一定要主动设置  SimpleMessageListenerContainer监听容器，监听应答队列
        container.setQueueNames("fixedReplyQueue");
        container.setMessageListener(fixedReplyRabbitTemplate());
        container.setConcurrentConsumers(100);
        container.setConcurrentConsumers(100);
        container.setPrefetchCount(250);
        return container;
    }
}
