/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
//AbstractSessionWebSocketMessageBrokerConfigurer实现了在handshake时获取httpsession，并且每次websocket消息发生时也刷新了httpsession的时间。同时在websocket session中加入了SPRING.SESSION.ID字段。
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private ChannelInterceptor myChannelInterceptor;

    @Autowired
    private HandshakeInterceptor myHandshakeInterceptor;

//    @Autowired
//    private WebSocketHandler myWebSocketHandler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry messageBrokerRegistry) {
        //启动简单Broker，客户端请求地址符合配置的前缀，消息才会发送到这个broker
        //客户端订阅当前服务端时需要添加以下请求前缀，topic一般用于广播推送，queue用于点对点推送
        //默认广播的前缀是/topic
        messageBrokerRegistry.enableSimpleBroker("/subscription");

        //如果不设置下面这一句，使用SimpMessagingTemplate.convertAndSendToUser("/fanqi/info")向指定用户推送消息时
        //订阅前缀只能为/user，例如前端订阅为/user/fanqi/info
        //服务端指定用户发送，客户端订阅的前缀，默认/user
        messageBrokerRegistry.setUserDestinationPrefix("/user_test");

        //客户端（html客户端、java客户端等）向服务端发送消息的请求前缀
        messageBrokerRegistry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //WebSocket客户端或SockJS客户端连接服务端访问的地址。
        registry.addEndpoint("/ws_test_1")
                .addInterceptors(myHandshakeInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();

        registry.addEndpoint("/ws_test_2")
                .addInterceptors(myHandshakeInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(myChannelInterceptor);
    }
}
