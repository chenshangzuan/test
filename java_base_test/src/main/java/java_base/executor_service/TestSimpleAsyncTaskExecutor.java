package java_base.executor_service;

import com.google.common.util.concurrent.*;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author kled
 * @version $Id: TestSimpleAsyncTaskExecutor.java, v 0.1 2019-02-15 16:11:23 kled Exp $
 */
public class TestSimpleAsyncTaskExecutor {

    public static void main(String[] args) throws InterruptedException {

        // **** Future task test ****
        //ExecutorService executor = Executors.newFixedThreadPool(1);
        //FutureTask<Integer> s = new FutureTask<Integer>(new Callable<Integer>() {
        //    @Override
        //    public Integer call() throws Exception {
        //        try {
            //            Thread.sleep(3000);
            //        } catch (InterruptedException e) {
            //            e.printStackTrace();
            //        }
        //        return 1;
        //    }
        //}){
        //    @Override
        //    protected void done() {
        //
        //        System.out.println("future task is done");
        //        super.done();
        //    }
        //};
        //System.out.println("111111");
        //executor.submit(s);

        //# spring
        //SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        //ListenableFuture<Integer> l = taskExecutor.submitListenable(() -> 2);
        //l.addCallback(new ListenableFutureCallback<Integer>() {
        //    @Override
        //    public void onFailure(Throwable ex) {
        //
        //    }
        //
        //    @Override
        //    public void onSuccess(Integer result) {
        //        try {
        //            Thread.sleep(2000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        //        System.out.println("operate success call back");
        //    }
        //});
        //System.out.println("main thread end");

        //guava
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(5));
        ListenableFuture<Boolean> booleanTask = service.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return true;
            }
        });

        Futures.addCallback(booleanTask, new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean result) {
                System.out.println("BooleanTask: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        }, service);
        System.out.println("main thread end");

        //结：线程池线程任务异步非阻塞回调
        //主线程等待任务完成
    }
}
