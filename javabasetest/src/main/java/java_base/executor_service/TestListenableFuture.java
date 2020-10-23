package java_base.executor_service;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import org.apache.commons.lang3.time.StopWatch;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: Kled
 * @version: TestListenableFuture.java, v0.1 2020-10-22 16:29 Kled
 */
public class TestListenableFuture {

    private static ExecutorService executorService = Executors.newFixedThreadPool(50);

    public static void main(String[] args) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ListenableFuture<?> listenableFuture1 = listeningExecutorService.submit(() -> test());
        ListenableFuture<?> listenableFuture2 = listeningExecutorService.submit(() -> test());
        ListenableFuture<?> listenableFuture3 = listeningExecutorService.submit(() -> test());

        ListenableFuture<List<Object>> listenableFutureList = Futures.allAsList(listenableFuture1, listenableFuture2, listenableFuture3);

        try {
            listenableFutureList.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        stopWatch.stop();
        System.out.println(stopWatch.getTime());
    }

    private static void test(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
