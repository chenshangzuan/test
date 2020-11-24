/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.controller;

import kled.test.bean.WsReponse;
import kled.test.bean.WsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

@Controller
@MessageMapping("/kled")
public class WsController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping("index")
    public String getIndex(){
        return "chat";
    }

    @MessageMapping("/test_ws_topic")
    //广播
    @SendTo("/subscription/ack")
    @ResponseBody
    public WsReponse helloWsTopic(@RequestBody WsRequest request){
        System.out.println("helloWsTopic");
        WsReponse wsReponse = new WsReponse();
        wsReponse.setMsg("hello" + request.getName());
        return wsReponse;
    }

    @MessageMapping("/test_ws_user/{transactionId}")
    //单一用户，可在同一租户账号(Principal)的多个Session内广播 broadcast = true, false表示当前租户并给予SessionId发送
    @SendToUser(value = "/subscription/ack", broadcast = false)
    @ResponseBody
    //WebSocket通过Http或Https握手，基于TCP协议发送、接收请求。HttpServletRequest无法获取
    public WsReponse helloWsUser(@RequestBody WsRequest request, StompHeaderAccessor stompHeaderAccessor
            , Principal principal, @Headers Map headers, Message message, @DestinationVariable("transactionId") String aa){
        System.out.println("test_ws_user: sessionId=" + stompHeaderAccessor.getSessionId()
                + ", principal=" + principal + ", headers=" + headers + ", message=" + message+ ", aa=" + aa + ", sessionAttr[test]=" + stompHeaderAccessor.getSessionAttributes().get("test"));
        WsReponse wsReponse = new WsReponse();
        wsReponse.setMsg("hello" + request.getName());
        return wsReponse;
    }

    @GetMapping("/broadcast_callback")
    @ResponseBody
    public String broadcastCallback(@RequestParam String name){
        WsReponse wsReponse = new WsReponse();
        wsReponse.setMsg(name);
        simpMessagingTemplate.convertAndSend("/subscription/ack", wsReponse);
        return "success";
    }

    @GetMapping("/single_callback")
    @ResponseBody
    public String singleCallback(@RequestParam String name){
        WsReponse wsReponse = new WsReponse();
        wsReponse.setMsg(name);
        simpMessagingTemplate.convertAndSendToUser("", "/subscription/ack", wsReponse);
        return "success";
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception t){
        t.printStackTrace();
        return t;
    }
}
