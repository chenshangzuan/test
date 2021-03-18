package kled.test.config;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.StatusListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author: Kled
 * @version: RedissonConfig.java, v0.1 2021-01-13 22:14 Kled
 */
@Configuration
public class RedissonConfig {

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    public void init(){
        RTopic topic = redissonClient.getTopic("topic1");
        topic.addListener(new StatusListener() {
            @Override
            public void onSubscribe(String s) {
                System.out.println("redisson StatusListener, msg=" + s);
            }

            @Override
            public void onUnsubscribe(String s) {

            }
        });
        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence charSequence, String s) {
                System.out.println("redisson MessageListener, charSequence=" + charSequence + ", s=" + s);
            }
        });

        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("queue1");
        new Thread(()-> {
            while (true) {
                try {
                    String t = blockingQueue.take();
                    System.out.println("get queue msg=" + t);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
