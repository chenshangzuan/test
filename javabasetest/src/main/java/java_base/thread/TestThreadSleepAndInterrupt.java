package java_base.thread;

/**
 * @author: Kled
 * @version: TestThreadSleepAndInterrupt.java, v0.1 2020-10-23 16:01 Kled
 */
public class TestThreadSleepAndInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread childThread1 = new Thread(() -> {
            while (true){
                //thread.isInterrupted()清除中断标记位
                if (Thread.interrupted()){
                    System.out.println("childThread1 interrupt state= " + Thread.currentThread().isInterrupted() +  ", after interrupted()");
                    System.out.println("childThread1 is stop");
                    break;
                }
                if(!Thread.interrupted()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //阻塞线程抛InterruptedException异常后，会将中断标记为置false
                        System.out.println("childThread1 -> after sleep Interrupted, state=" + Thread.currentThread().isInterrupted());
                        e.printStackTrace();
                        break;
                    }
                    System.out.println("childThread1 is running");
                }
            }
        });
        childThread1.start();

        Thread childThread2 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    //thread.isInterrupted()不清除中断标记位
                    System.out.println("childThread2 interrupt state= " + Thread.currentThread().isInterrupted() +  ", after isInterrupted()");
                    System.out.println("childThread2 is stop");
                    break;
                }

                if(!Thread.currentThread().isInterrupted()){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //阻塞线程抛InterruptedException异常后，会将中断标记为置false
                        System.out.println("childThread2 -> after sleep Interrupted, state=" + Thread.currentThread().isInterrupted());
                        e.printStackTrace();
                        break;
                    }
                    System.out.println("childThread2 is running");
                }

            }
        });
        childThread2.start();

        Thread childThread3 = new Thread(() -> {
            while (true){
                if (Thread.currentThread().isInterrupted()){
                    //thread.isInterrupted()不清除中断标记位
                    System.out.println("childThread3 interrupt state= " + Thread.currentThread().isInterrupted() +  ", after isInterrupted()");
                    System.out.println("childThread3 is stop");
                    break;
                }
            }
        });
        childThread3.start();

        Thread.sleep(2000);
        childThread1.interrupt();
        childThread2.interrupt();
        childThread3.interrupt();

        Thread.sleep(5000);
    }

    private static void testThreadSleepInterrupt() throws InterruptedException {
        Thread childThread1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("child thread is awake");
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                //do something
            }
        });
        childThread1.start();
        Thread.sleep(100);
        System.out.println(childThread1.getState());

        //中断childThread(需处理), TIMED_WAITING -> RUNNING
        childThread1.interrupt();
        for (int i = 0; i < 10; i++) {
            System.out.println(childThread1.getState());
        }

        Thread.sleep(5000);
    }
}
