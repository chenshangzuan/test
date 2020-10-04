package localTest;

import java.util.concurrent.TimeUnit;

/**
 * @author chenpc
 * @version $Id: localTest.TestThread.java, v 0.1 2018-05-02 22:55:50 chenpc Exp $
 */
public class TestThread {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            System.out.println("thread start");
            TestThread testThread = new TestThread();
            testThread.test();
        });

        Thread.currentThread().interrupted();
        t.start();

        TimeUnit.SECONDS.sleep(5);
        try {
            t.wait(2000);
        } catch (IllegalMonitorStateException e) {
            System.out.println("wait/notify/notifyAll方法必须在Synchronized申明内");
            e.printStackTrace();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    private synchronized void test()  {
        System.out.println("hold the monitor lock");
        try {
            this.wait(2000); //持有monitor锁的对象才能调用wait()/notify/notifyAll方法
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("release the lock and awake");
    }

}
