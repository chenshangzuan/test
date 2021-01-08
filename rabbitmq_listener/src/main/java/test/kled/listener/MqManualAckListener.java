/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package test.kled.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class MqManualAckListener {

    @RabbitListener(queues = "testQ5", containerFactory = "manualAckMessageListenerContainer")
    public void test3(Message message, Channel channel) throws IOException {
        //channel.basicAck(long,boolean); 确认收到消息，消息将被队列移除，false只确认当前consumer一个消息收到，true确认所有consumer获得的消息。
        //channel.basicNack(long,boolean,boolean); 确认否定消息，第一个boolean表示一个consumer还是所有，第二个boolean表示requeue是否重新回到队列，true重新入队。

        //注：unacked的消息在consumer切断连接后(重启)，会自动回到队头。
        try{
            //签收消息
            System.out.println("---签收消息");
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        }catch (Exception e){
            System.out.println("---签收消息异常");
            //处理抛出异常，如果重新把消息放回队列则requeue设置为true否则设置为false
            channel.basicNack(message.getMessageProperties().getDeliveryTag(),false,true);
        }
    }
}
