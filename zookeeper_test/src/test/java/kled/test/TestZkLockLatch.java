package kled.test;

import com.zenlayer.commons.locks.LockExecutor;
import com.zenlayer.commons.locks.LockType;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author: Kled
 * @version: TestZkLeaderLatch.java, v0.1 2020-12-01 16:21 Kled
 */
public class TestZkLockLatch extends SpringTestZkSpringBootApplicationTests {

    @Autowired
    private LockExecutor<InterProcessMutex> lockExecutor;

    @Test
    public void testZkLock() throws Exception {
        System.out.println("try lock");
        InterProcessMutex lock = lockExecutor.tryLock(LockType.LOCK, "lock-1", 1000L, 1000L, false, false);
        //节点Path: /zenCommonLock/lock/lock-1/_c_44f23f94-6bf9-40ff-9272-f39aacd5323e-lock-0000000000(临时节点)
        System.out.println("locked, lock=" + lock);
        Thread.sleep(5000);
        lockExecutor.unlock(lock);
        System.out.println("lock released");
        Thread.sleep(10000);
        System.out.println("finish");
    }

    @Test
    public void testZkReadAndWriteLock() throws Exception {
        System.out.println("try lock");
        InterProcessMutex lock1 = lockExecutor.tryLock(LockType.READ_LOCK, "lock-1", 1000L, 1000L, false, false);
        System.out.println("main thread get read lock=" + lock1);
        new Thread(() -> {
            try {
                //读锁共享
                InterProcessMutex lock2 = lockExecutor.tryLock(LockType.READ_LOCK, "lock-1", 1000L, 1000L, false, false);
                System.out.println(Thread.currentThread().getName() + " get read lock=" + lock2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                //不同线程读写锁互斥
                InterProcessMutex lock2 = lockExecutor.tryLock(LockType.WRITE_LOCK, "lock-1", 1000L, 1000L, false, false);
                System.out.println(Thread.currentThread().getName() + " get write lock=" + lock2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(10000);
        lockExecutor.unlock(lock1);
        System.out.println("main thread lock released");
        Thread.sleep(10000);
        System.out.println("finish");
    }

    @Test
    public void testZkReadLockUpgrade() throws Exception {
        System.out.println("try lock");
        InterProcessMutex lock1 = lockExecutor.tryLock(LockType.READ_LOCK, "lock-1", 1000L, 1000L, false, false);
        System.out.println("main thread get read lock=" + lock1);
        InterProcessMutex lock2 = lockExecutor.tryLock(LockType.WRITE_LOCK, "lock-1", 1000L, 1000L, false, false);
        //读锁升级写锁失败
        System.out.println("main thread get write lock=" + lock2);
        Thread.sleep(10000);
        lockExecutor.unlock(lock1);
        System.out.println("main thread lock released");
        Thread.sleep(10000);
        System.out.println("finish");
    }

    @Test
    public void testZkWriteLockDowngrade() throws Exception {
        System.out.println("try lock");
        InterProcessMutex lock1 = lockExecutor.tryLock(LockType.WRITE_LOCK, "lock-1", 1000L, 1000L, false, false);
        System.out.println("main thread get write lock=" + lock1);
        InterProcessMutex lock2 = lockExecutor.tryLock(LockType.READ_LOCK, "lock-1", 1000L, 1000L, false, false);
        //同一线程下，支持写锁降级读锁
        System.out.println("main thread get read lock=" + lock2);
        new Thread(() -> {
            try {
                //降级之后，其他线程的读锁无法获取
                InterProcessMutex lock3 = lockExecutor.tryLock(LockType.READ_LOCK, "lock-1", 1000L, 1000L, false, false);
                System.out.println(Thread.currentThread().getName() + " get read lock=" + lock3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(10000);
        lockExecutor.unlock(lock1);
        System.out.println("main thread lock released");
        Thread.sleep(10000);
        System.out.println("finish");
    }
}
