package java_base.thread;

/**
 * @author: Kled
 * @version: TestThreadJoin.java, v0.1 2020-10-23 15:32 Kled
 */
public class TestThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        System.out.println("main thread start");

        Thread childThread = new Thread(() -> {
            try {
                System.out.println("child thread start");
                System.out.println("main thread state=" + mainThread.getState());
                Thread.sleep(1000);
                System.out.println("child thread finish");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        childThread.start();
        //会将主线程挂起WAITINg
        childThread.join();
        System.out.println("main thread stop");

    }
}
