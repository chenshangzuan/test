package localTest;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kled
 * @version $Id: TestVolatile.java, v 0.1 2018-12-20 16:30:44 kled Exp $
 */
public class TestVolatile extends Thread {

    private volatile boolean isRunning = true; //直接访问堆内存，保证最终一致性
    //private boolean isRunning = true;

    private AtomicInteger count1 = new AtomicInteger(0);

    private int count2 = 0;

    private Integer count3 = 0;

    private String count4 = "";

    private static final Integer count5 = 0;

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        System.out.println("wait for running");
        while (isRunning) {
            //count1.getAndIncrement(); 调用count时会去访问堆内存，即会读取最新的isRunning值
            //System.out.println(count2++); count2++值为基本数据类型，在方法栈中
            //String 类型 count4 = "test";
            //常量 static final count5++;
            //结论：当对对象的引用属性进行操作时，会去堆内存(引用指向堆内存)。其他基本数据类型及常量则直接访问
        }
        System.out.println("stop running");
    }
}

class Run {
    public static void main(String[] args) throws InterruptedException {
        TestVolatile t = new TestVolatile();
        t.start();
        Thread.sleep(1000);
        System.out.println("prepare stop thread");
        t.setRunning(false);
        Thread.sleep(5000);
    }
}
