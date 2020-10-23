package java_base.thread;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author: Kled
 * @version: TestObjectWaitAndNotify.java, v0.1 2020-10-23 14:55 Kled
 */
public class TestObjectWaitAndNotify {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();

        //Queue<Integer> targets = new LinkedBlockingQueue<>();
        Queue<Integer> targets = new ArrayBlockingQueue<>(10);

        Thread consumer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    System.out.println("consumer start working!!!");
                    while (!targets.isEmpty()){
                        System.out.println("consumer target=" + targets.poll());
                    }
                    try {
                        Thread.sleep(1000);
                        //唤醒生产者
                        lock.notifyAll();
                        System.out.println("consumer stop working!!!");
                        //唤醒停止消费者
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        consumer.start();

        Thread producer = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    //System.out.println("consumer state=" + consumer.getState());
                    System.out.println("producer start working!!!");
                    for (int i = 0; i < new Random().nextInt(5); i++) {
                        targets.add(i);
                        System.out.println("producer target=" + i);
                    }
                    System.out.println("producer stop working!!!");
                    //唤醒消费者
                    lock.notifyAll(); //当前线程不会释放对象锁
                    try {
                        Thread.sleep(1000);
                        //生产者停止
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        producer.start();

        Thread.sleep(5000);
    }
}
