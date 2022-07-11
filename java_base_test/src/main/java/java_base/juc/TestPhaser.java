package java_base.juc;

import EDU.oswego.cs.dl.util.concurrent.Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

/**
 * @author Kled
 * @date 2022/5/6 10:28 PM
 */
public class TestPhaser {
    public static void main(String[] args) {
        Phaser phaser = new Phaser();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        // 1. 注册一个 party
        phaser.register();
        for (int i = 0; i < 10; i++) {
            phaser.register();
            executorService.submit(() -> {
                // 3. 每个线程到这里进行阻塞，等待所有线程到达栅栏
//                System.out.println("phaser -> Registered Parties=" + phaser.getRegisteredParties());
//                System.out.println("phaser -> Unarrived Parties=" + phaser.getUnarrivedParties());
                phaser.arriveAndAwaitAdvance();
                // doWork()
            });
        }
        phaser.arriveAndAwaitAdvance();
    }
}
