package java_base.thread;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Kled
 * @version: TestCondition.java, v0.1 2020-10-25 15:53 Kled
 */
public class TestCondition {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        //Condition condition = lock.newCondition(); // ==> new ConditionObject() ==> Sync extends AbstractQueuedSynchronizer内部类
        Condition notFull = lock.newCondition();
        Condition notEmpty = lock.newCondition();

        Queue<Integer> products = new LinkedBlockingQueue<>();
        Thread consumer = new Thread(() -> {
            while (true){
                lock.lock();
                System.out.println("consumer lock hold count:"+lock.getHoldCount());
                while (!products.isEmpty()){
                    Integer product = products.poll();
                    System.out.println("consumer product=" + product);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //唤醒生成者
                notFull.signal();

                try {
                    //阻塞消费者线程，自动释放锁
                    notEmpty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        });

        Thread producer = new Thread(() -> {
            while (true){
                lock.lock();
                System.out.println("producer lock hold count:"+lock.getHoldCount());
                while (products.size() <= 5){
                    Integer product = new Random().nextInt(10);
                    products.add(product);
                    System.out.println("producer product=" + product);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //唤醒消费者
                notEmpty.signal();

                try {
                    //阻塞生产者线程，自动释放锁
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.start();
        producer.start();
        Thread.sleep(5000);
    }
}
