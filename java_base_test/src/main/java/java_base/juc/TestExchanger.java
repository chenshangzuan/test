package java_base.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

/**
 * @author Kled
 * @date 2022/5/13 10:55 AM
 */
public class TestExchanger {

    public static void main(String[] args) throws InterruptedException {

        //多线程并发交换数据，会随机匹配交换
        Exchanger<Integer> exchanger = new Exchanger<>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Thread producer = new Thread(() -> {
                System.out.println("producer-" + finalI + " exchange " + finalI);
                try {
                    Integer exchange = exchanger.exchange(finalI);
                    System.out.println("producer-" + finalI + " get " + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });

            producer.start();
        }

        for (int i = 3; i < 6; i++) {
            int finalI = i;
            Thread consumer = new Thread(() -> {
                System.out.println("consumer-" + finalI + " exchange " + finalI);
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Integer exchange = exchanger.exchange(finalI);
                    System.out.println("consumer-" + finalI + " get " + exchange);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
            consumer.start();
        }
        countDownLatch.countDown();
        Thread.currentThread().join();
    }
}
