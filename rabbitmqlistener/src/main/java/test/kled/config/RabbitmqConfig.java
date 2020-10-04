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
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author luofeng
 * @version $Id: RabbitmqConfig.java, v 0.1 2017年9月14日 下午3:50:12 luofeng Exp $
 */
@Configuration
public class RabbitmqConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * BgpStatus 路由器，队列，绑定关系配置
     *
     * @return
     */
    @Bean(name = "testQ")
    public Queue testQ() {
        return new Queue("testQ", true);
    }

    @Bean
    public DirectExchange testdirectExchange() {
        return new DirectExchange("test.direct");
    }

    @Bean
    public Binding bindingTestQ() {
        return BindingBuilder.bind(testQ()).to(testdirectExchange()).with("test_direct_q");
    }

    @Bean(name = "testQ2")
    public Queue testQ2() {
        return new Queue("testQ2", true);
    }

    @Bean
    public DirectExchange test2directExchange() {
        return new DirectExchange("test2.direct");
    }

    @Bean
    public Binding bindingTest2Q() {
        return BindingBuilder.bind(testQ2()).to(test2directExchange()).with("test2_direct_q");
    }

    @Bean(name = "testQ3")
    public Queue testQ3() {
        return new Queue("testQ3", true);
    }

    @Bean
    public DirectExchange test3directExchange() {
        return new DirectExchange("test3.direct");
    }

    @Bean
    public Binding bindingTest3Q() {
        return BindingBuilder.bind(testQ3()).to(test3directExchange()).with("test3_direct_q");
    }

    @Bean(name = "testQ4")
    public Queue testQ4() {
        return new Queue("testQ4", true);
    }

    @Bean
    public DirectExchange test4directExchange() {
        return new DirectExchange("test4.direct");
    }

    @Bean
    public Binding bindingTest4Q() {
        return BindingBuilder.bind(testQ4()).to(test4directExchange()).with("test4_direct_q");
    }

    @Bean(name = "fixedReplyQueue")
    public Queue fixedReplyQueue() {
        return new Queue("fixedReplyQueue", true);
    }

//    @Bean
//    public RabbitAdmin rabbitAdmin() {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter converter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
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
