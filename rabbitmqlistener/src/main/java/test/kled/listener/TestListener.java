/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.listener;

import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "testQ", containerFactory = "rabbitListenerContainerFactory")
public class TestListener {

//    @RabbitHandler
//    //content_type: empty
//    public void test(byte[] message){
//        System.out.println("byte msg = " + message);
//    }
//
//    @RabbitHandler
//    //content_type: text/plain
//    public void test1(String message){
//        System.out.println("string msg = " + message);
//    }

    @RabbitHandler
    //content_type: application/json
    //@RabbitListener(queues = "testQ", containerFactory = "rabbitListenerContainerFactory") //必须注解在方法上才能获取MessageConvert
    public void test2(@Payload MyMsg message){
        System.out.println("Bean msg = " + message);
    }

    @RabbitHandler
    //content_type: application/json
    public void test3(Map<String, String> message){
        System.out.println("map msg = " + message);
    }

    public static class MyMsg{
        private String str;

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return "MyMsg{" +
                    "str='" + str + '\'' +
                    '}';
        }
    }
}
