package localTest;

import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.util.concurrent.ListenableFutureTask;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author kled
 * @version $Id: TestExecutorService.java, v 0.1 2018-12-27 11:28:08 kled Exp $
 */
public class TestExecutorService {

    private static final ExecutorService execurtor = Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws InterruptedException {
        //testFutureTask();

        //spring
        testListenableFutureTask();
    }

    private static void testListenableFutureTask() throws InterruptedException {
        ListenableFutureTask<String> task = new ListenableFutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(5000); // 模拟耗时操作
                return "success";
            }
        });

        // 注册回调事件
        task.addCallback(new ListenableFutureCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    //task.get() = result
                    System.out.println("result = " + result);
                    System.out.println("调用成功，返回结果是:" + task.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
                System.out.println("调用失败了...");
            }
        });

        execurtor.submit(task);
        Thread.sleep(100000);
    }

    private static void testFutureTask() {
        Task task = new Task();// 新建异步任务
        FutureTask<Integer> future = new FutureTask<Integer>(task) {
            // 异步任务执行完成，回调（可实现非阻塞式回调）
            @Override
            protected void done() {
                try {
                    System.out.println("future.done():" + get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        // 创建线程池（使用了预定义的配置）
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(future);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        // 可以取消异步任务
        // future.cancel(true);

        try {
            // 阻塞，等待异步任务执行完毕-获取异步任务的返回值
            System.out.println("future.get():" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

}

// 异步任务
class Task implements Callable<Integer> {
    // 返回异步任务的执行结果
    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 10; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + "_" + i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
}
