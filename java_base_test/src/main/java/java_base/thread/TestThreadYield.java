package java_base.thread;

/**
 * @author: Kled
 * @version: TestThreadYield.java, v0.1 2020-10-23 15:44 Kled
 */
public class TestThreadYield {

    public static void main(String[] args) {
        Thread threadB = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("ThreadB-" + i);
                //让出CPU资源，
                Thread.yield();
                //System.out.println(Thread.currentThread().getState());
            }
        });

        Thread threadA = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println("ThreadA-" + i);
                //Thread.yield();
                //yield不支持中断
                threadB.interrupt();
            }
        });

        // 设置优先级:MIN_PRIORITY最低优先级1;NORM_PRIORITY普通优先级5;MAX_PRIORITY最高优先级10
        threadA.setPriority(Thread.MIN_PRIORITY);
        threadB.setPriority(Thread.MAX_PRIORITY);

        threadA.start();
        threadB.start();
    }
}
