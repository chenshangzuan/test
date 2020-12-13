package kled.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: Kled
 * @version: TestZkSemaphore.java, v0.1 2020-12-11 15:32 Kled
 */
public class TestZkSemaphore extends SpringTestZkSpringBootApplicationTests{

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void testZkSemaphore() throws InterruptedException {
        //设置最大并发量
        String path = "/semaphore";
        InterProcessSemaphoreV2 semaphoreV2 = new InterProcessSemaphoreV2(curatorFramework, path, 1);
        new Thread(() -> {
            try {
                Lease lease = semaphoreV2.acquire();
                System.out.println(Thread.currentThread().getName() + " lease = " + lease);
                Thread.sleep(1000);
                lease.close();
                System.out.println(Thread.currentThread().getName() + " release lease = " + lease);
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " acquire semaphore fail");
            }
        }).start();

        new Thread(() -> {
            try {
                Lease lease = semaphoreV2.acquire();
                System.out.println(Thread.currentThread().getName() + " lease = " + lease);
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " release lease = " + lease);
                lease.close();
            } catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " acquire semaphore fail");
            }
        }).start();
        Thread.sleep(5000);
    }
}
