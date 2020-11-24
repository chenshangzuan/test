/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.WsInterceptors;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
public class MyHandshakeInterceptor implements HandshakeInterceptor{
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("beforeHandshake -> " + request.getPrincipal() + ", " + request.getClass().getCanonicalName() + ", " + attributes);
        if(request instanceof ServletServerHttpRequest){
            HttpSession httpSession = ((ServletServerHttpRequest) request).getServletRequest().getSession(false);
            System.out.println("ws httpSession=" + httpSession); //null
            ((ServletServerHttpRequest) request).getServletRequest().getSession(true);
            attributes.put("test", "session");
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {

        //握手成功后,
        System.out.println("afterHandshake -> " + request.getPrincipal() + ", " + request.getClass().getCanonicalName());
    }
}
