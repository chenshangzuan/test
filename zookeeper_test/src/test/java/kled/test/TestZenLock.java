package kled.test;

import com.nepxion.aquarius.lock.LockExecutor;
import com.nepxion.aquarius.lock.LockTemplate;
import com.nepxion.aquarius.lock.entity.ExecutionResult;
import com.nepxion.aquarius.lock.entity.LockType;
import kled.test.service.HelloWorldService;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.concurrent.Executor;

/**
 * @author: Kled
 * @version: TestZenLock.java, v0.1 2021-01-20 14:54 Kled
 */
public class TestZenLock extends SpringTestZkSpringBootApplicationTests {

    @Autowired
    private LockExecutor<InterProcessMutex> lockExecutor;

    @Autowired
    private LockTemplate lockTemplate;

    @Autowired
    private HelloWorldService helloWorldService;

    @Test
    public void testTryLock() throws Exception {
        InterProcessMutex interProcessMutex = lockExecutor.tryLock(LockType.LOCK, "klde.test");
        System.out.println("try lock");
        System.out.println(interProcessMutex.toString());
        Thread.sleep(10000);
        System.out.println("release lock");
        lockExecutor.unlock(interProcessMutex);
    }

    @Test
    public void testLock() throws Exception {
        String lockName = "klde.test";
        new Thread(() -> {
            InterProcessMutex interProcessMutex = null;
            try {
                interProcessMutex = lockExecutor.lock(LockType.LOCK, lockName);
                System.out.println("thread1 get lock");

                Thread.sleep(3000);
                lockExecutor.unlock(interProcessMutex);
                System.out.println("thread1 release lock");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            InterProcessMutex interProcessMutex = null;
            try {
                interProcessMutex = lockExecutor.lock(LockType.LOCK, lockName);
                System.out.println("thread2 get lock");

                Thread.sleep(3000);
                lockExecutor.unlock(interProcessMutex);
                System.out.println("thread2 release lock");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(10000);
    }

    @Test
    public void testTryLockTemplate() throws Throwable {
        ExecutionResult result = lockTemplate.invoke(new LockTemplate.ExecutionBlock<String>() {
            @Override
            public String proceed() throws Throwable {
                System.out.println("testLockTemplate");
                return "testTryLockTemplate";
            }
        }, LockType.LOCK, "kled.lock213", false);
        System.out.println(result);
    }

    @Test
    public void testLockTemplate() throws Throwable {
        String lockName = "klde.test";
        new Thread(() -> {
            try {
                lockTemplate.invoke(new LockTemplate.ExecutionBlock<String>() {
                    @Override
                    public String proceed() throws Throwable {
                        System.out.println("thread1 get lock");

                        Thread.sleep(3000);
                        System.out.println("thread1 release lock");
                        return "testLockTemplate";
                    }
                }, LockType.LOCK, lockName, true);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                lockTemplate.invoke(new LockTemplate.ExecutionBlock<String>() {
                    @Override
                    public String proceed() throws Throwable {
                        System.out.println("thread2 get lock");

                        Thread.sleep(3000);
                        System.out.println("thread2 release lock");
                        return "testLockTemplate";
                    }
                }, LockType.LOCK, lockName, true);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }).start();

        Thread.sleep(10000);
    }

    @Test
    public void testLockAop(){
        helloWorldService.getHelloMessage();
    }

}
