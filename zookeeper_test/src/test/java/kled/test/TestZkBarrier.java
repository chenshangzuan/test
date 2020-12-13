package kled.test;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

/**
 * @author: Kled
 * @version: TestZkSemaphore.java, v0.1 2020-12-11 15:32 Kled
 */
public class TestZkBarrier extends SpringTestZkSpringBootApplicationTests{

    @Autowired
    private CuratorFramework curatorFramework;

    @Test
    public void testZkBarrier() throws Exception {
        //并发同步
        String path = "/barrier";
        DistributedBarrier distributedBarrier = new DistributedBarrier(curatorFramework, path);
        distributedBarrier.setBarrier();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " wait on barrier");
                distributedBarrier.waitOnBarrier();
                System.out.println(Thread.currentThread().getName() + " is working");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " wait on barrier");
                distributedBarrier.waitOnBarrier();
                System.out.println(Thread.currentThread().getName() + " is working");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " wait on barrier");
                //等待100ms
                distributedBarrier.waitOnBarrier(100, TimeUnit.MILLISECONDS);
                System.out.println(Thread.currentThread().getName() + " is working");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " remove barrier");
        distributedBarrier.removeBarrier();
        Thread.sleep(5000);
    }

    @Test
    public void testZkDoubleBarrier() throws Exception {
        //并发同步
        //每个线程需自己维护一个DistributedDoubleBarrier, 注册的路径为随机数
        //各个实例的memberQty栅栏数由业务场景决定，一般一致，同时进入及退出
        String path = "/doubleBarrier";
        new Thread(() -> {
            try {
                DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(curatorFramework, path, 3);
                System.out.println(Thread.currentThread().getName() + " wait on barrier");
                distributedDoubleBarrier.enter();
                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(100);
                System.out.println(Thread.currentThread().getName() + " wait for leaving");
                distributedDoubleBarrier.leave();
                System.out.println(Thread.currentThread().getName() + " leave");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(curatorFramework, path, 3);
                System.out.println(Thread.currentThread().getName() + " wait on barrier");
                distributedDoubleBarrier.enter();
                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(200);
                System.out.println(Thread.currentThread().getName() + " wait for leaving");
                distributedDoubleBarrier.leave();
                System.out.println(Thread.currentThread().getName() + " leave");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                DistributedDoubleBarrier distributedDoubleBarrier = new DistributedDoubleBarrier(curatorFramework, path, 3);
                System.out.println(Thread.currentThread().getName() + " wait on barrier");
                distributedDoubleBarrier.enter();
                System.out.println(Thread.currentThread().getName() + " is working");
                Thread.sleep(300);
                System.out.println(Thread.currentThread().getName() + " wait for leaving");
                distributedDoubleBarrier.leave();
                System.out.println(Thread.currentThread().getName() + " leave");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(5000);
    }
}
