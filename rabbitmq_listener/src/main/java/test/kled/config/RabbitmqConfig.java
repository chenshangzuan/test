/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.config;

import org.assertj.core.util.Maps;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author kled
 * @version $Id: RabbitmqConfig.java, v 0.1 2020年9月14日 下午3:50:12 kled Exp $
 */
@Configuration
public class RabbitmqConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

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

    @Bean(name = "testQ5")
    public Queue testQ5() {
        return new Queue("testQ5", true);
    }

    @Bean
    public DirectExchange test5directExchange() {
        return new DirectExchange("test5.direct");
    }

    @Bean
    public Binding bindingTest5Q() {
        return BindingBuilder.bind(testQ5()).to(test5directExchange()).with("test5_direct_q");
    }

    @Bean(name = "testQ6")
    public Queue testQ6() {
        return new Queue("testQ6", true);
    }

    @Bean
    public DirectExchange test6directExchange() {
        return new DirectExchange("test6.direct");
    }

    @Bean
    public Binding bindingTest6Q() {
        return BindingBuilder.bind(testQ6()).to(test5directExchange()).with("test6_direct_q");
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
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                //记录每次消息传递的状态，失败重试
                System.out.println(String.format("rabbitTemplate -> confirm, correlationData=%s, ack=%s, cause=%s", Optional.ofNullable(correlationData).orElse(null), ack, cause));
            }
        });

        //mandatory为true时，路由失败的消息会被返回，触发ReturnCallback。否则直接丢弃
        template.setMandatory(true);
        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println(String.format("rabbitTemplate -> returnedMessage, message=%s, replyCode=%s, replyText=%s, exchange=%s, routingKey=%s", new String(message.getBody()), replyCode, replyText, exchange, routingKey));
            }
        });

        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory manualAckMessageListenerContainer() {
        SimpleRabbitListenerContainerFactory container = new SimpleRabbitListenerContainerFactory();
        container.setConnectionFactory(connectionFactory);
        // 并发消费者数量
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(10);

        //参考ConditionalRejectingErrorHandler#handleError和BlockingQueueConsumer#rollbackOnExceptionIfNecessary
        //致命错误Fatal Exception: MessageConversionException、MessageConversionException、MethodArgumentResolutionException、NoSuchMethodException、ClassCastException
        //如何配置死信队列，则抛出ImmediateAcknowledgeAmqpException，否则会向上抛出AmqpRejectAndDontRequeueException

        //Auto模式
        //如果消息成功被消费（成功的意思是在消费的过程中没有抛出异常），则自动确认
        //当抛出 AmqpRejectAndDontRequeueException 异常的时候，则消息会被拒绝，且 requeue = false（不重新入队列）
        //当抛出 ImmediateAcknowledgeAmqpException 异常，则消息直接被确认消费
        //当抛出 ImmediateRequeueAmqpException 移除，则直接回到队列中
        //其他的异常，则消息会被拒绝，且 requeue = true，此时会发生死循环，可以通过 setDefaultRequeueRejected=false（默认是true）去设置抛弃消息
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);

        //被拒绝的消息(消费者异常)默认是否回到队列
        container.setDefaultRequeueRejected(true);

        //限流表示每次消费端拉取一条消息进行消费直到收到确认完成后在拉取下一条
        container.setPrefetchCount(1);
        container.setMessageConverter(new Jackson2JsonMessageConverter());
        return container;
    }
}
