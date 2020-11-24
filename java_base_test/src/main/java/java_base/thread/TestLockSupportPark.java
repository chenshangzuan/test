package java_base.thread;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: Kled
 * @version: TestLockSupportPark.java, v0.1 2020-10-23 17:13 Kled
 */
public class TestLockSupportPark {

    public static void main(String[] args) throws InterruptedException {
        Thread childThread = new Thread(() -> {
            System.out.println("childThread -> stop");
            LockSupport.park();
            System.out.println("childThread -> start");
        });
        childThread.start();

        Thread.sleep(3000);
        System.out.println("main thread sleep after 3s, unpark child thread");
        LockSupport.unpark(childThread);
        //"许可"不会叠加，一个park会使用调所有的许可
        //LockSupport.unpark(childThread);
        Thread.sleep(1000);
    }
}
