package localTest;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThreadPoolExecutor {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //1)FixedThreadPool 和 SingleThreadPool:允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量的请求，从而导致 OOM。
        //2)CachedThreadPool 和 ScheduledThreadPool:允许的创建线程数量为 Integer.MAX_VALUE，可能会创建大量的线程，从而导致 OOM。

        //ExecutorService e = Executors.newCachedThreadPool();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(1));
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setRejectedExecutionHandler(new TestHandler());
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        // List<Integer> list = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger(0);
        Future<AtomicInteger> result = null;
        try {
            for (int i = 0; i < 8; i++) {
                result = executor.submit(new Callable<AtomicInteger>() {

                    @Override
                    public AtomicInteger call() throws Exception {
                        //list.add(new Random().nextInt(10));
                        atomicInteger.set(atomicInteger.get() + 1);
                        Thread.sleep(5000);
                        return atomicInteger;
                    }
                });
                System.out.println(i);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("error " + e.getMessage());
        }

        //System.out.println(result.get());
        executor.shutdown();  //启动一次顺序关闭，执行以前提交的任务，但不接受新任务。
        while (!executor.isTerminated()) {  //若关闭后所有任务都已完成，则返回true。

        }
    }

    static class TestHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //r.run();
            new Thread(r).start();
            System.out.println("stop");
        }
    }
}
