/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.WsInterceptors;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Component
public class MyChannelInterceptor implements ChannelInterceptor {

//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
////        MessageHeaders headers = message.getHeaders();
////        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
////        System.out.println(sessionId);
//
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//        StompCommand command = accessor.getCommand();
//        //System.out.println(accessor.getCommand());
//        //检测用户订阅内容（防止用户订阅不合法频道）
//        if (StompCommand.CONNECT.equals(command)) {
//            System.out.println(this.getClass().getCanonicalName() + " 用户连接成功=" + accessor.getDestination());
//            // 如果该用户订阅的频道不合法直接返回null前端用户就接受不到该频道信息
//            return message;
//        }
//        //如果用户断开连接
//        if (StompCommand.DISCONNECT.equals(command)){
//            System.out.println(this.getClass().getCanonicalName() + "用户断开连接成功");
//            return message;
//        }
//
//        return message;
//    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        MessageHeaders headers = message.getHeaders();
        String sessionId = SimpMessageHeaderAccessor.getSessionId(headers);
        //System.out.println(sessionId);

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand command = accessor.getCommand();
        //System.out.println(accessor.getCommand());
        //检测用户订阅内容（防止用户订阅不合法频道）
//        if (StompCommand.CONNECT.equals(command)) {
//            System.out.println(this.getClass().getCanonicalName() + " 用户连接成功=" + accessor.getDestination());
//            // 如果该用户订阅的频道不合法直接返回null前端用户就接受不到该频道信息
//            // biz code
//        }
        //如果用户断开连接
        if (StompCommand.CONNECT.equals(command)){
            System.out.println(sessionId + "用户创建连接成功");
            // biz code
            // 记录连接的关联 // Map<String/*sessionId*/, List<Runnable>>
        }
        if (StompCommand.DISCONNECT.equals(command)){
            System.out.println(sessionId + "用户断开连接成功");
            // biz code
            // 删除所有任务 Map<String/*sessionId*/, List<Runnable>>
        }

        if (StompCommand.SUBSCRIBE.equals(command)){
            System.out.println(sessionId + "用户订阅成功，创建任务消费线程, 返回数据");
            // biz code
            // 创建任务消费线程
            //System.out.println(accessor.getDestination());
        }
    }
}
