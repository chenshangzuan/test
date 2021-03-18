package kled.test.bean;

import org.springframework.stereotype.Component;

/**
 * @author: Kled
 * @version: RedisSubHandler.java, v0.1 2021-01-31 20:33 Kled
 */
@Component
public class RedisSubHandler {
    public void message1(String message) throws InterruptedException {
        //业务处理
        System.out.println("收到topic1订阅消息:" + message);

    }

    public void message2(String message) throws InterruptedException {
        //业务处理
        System.out.println("收到topic2订阅消息:" + message);
    }
}
