package java_base.guava;

import com.google.common.base.Stopwatch;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chenpc
 * @version $Id: java_base.guava.TestCacheBuilder.java, v 0.1 2018-05-09 09:43:08 chenpc Exp $
 */
public class TestCacheBuilder {
    private static Callable<String> callable = new Callable<String>() {
        @Override
        public String call() throws Exception {
            System.out.println("begin to mock query db...");
            Thread.sleep(2000);
            System.out.println("success to mock query db...");
            return UUID.randomUUID().toString();
        }
    };

    // guava线程池,用来产生ListenableFuture
    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    private static LoadingCache<String, String> cache = CacheBuilder.newBuilder()
            //.expireAfterAccess(1, TimeUnit.SECONDS) 1s内无读、写请求则会缓存失效，失效后并发请求会都阻塞
            //.expireAfterWrite(1, TimeUnit.SECONDS) 1s内无写请求则会缓存失效，失效后并发请求会都阻塞
            .refreshAfterWrite(1, TimeUnit.SECONDS) //1s内无写请求则会缓存失效，此时只有一个写请求会阻塞，其余读旧值。也可以重写reload方法实现异步加载
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String key) throws Exception {
                    return callable.call();   //expire -> load
                }

                @Override
                public ListenableFuture<String> reload(String key, String oldValue) throws Exception {
                    System.out.println("......后台线程池异步刷新:" + key);  //refresh -> reload 可实现异步刷新，其他线程先获取旧值
                    return service.submit(callable);
                }
            });

    private static CountDownLatch latch = new CountDownLatch(1);


    public static void main(String[] args) throws Exception {
        cache.put("name", "aty");
        Thread.sleep(1500);

        for (int i = 0; i < 8; i++) {
            startThread(i);
        }

        // 让线程运行
        latch.countDown();

    }

    private static void startThread(int id) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getName() + "...begin");
                    latch.await();
                    Stopwatch watch = Stopwatch.createStarted();
                    System.out.println(Thread.currentThread().getName() + "...value..." + cache.get("name"));
                    watch.stop();
                    System.out.println(Thread.currentThread().getName() + "...finish,cost time=" + watch.elapsed(TimeUnit.SECONDS));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        t.setName("Thread-" + id);
        t.start();
    }


    private void testCpeCache(){
        //@Autowired
        //private QueryService queryService;
        //@Autowired
        //private CpeDAO cpeDAO;
        //
        //private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        //
        //@java_base.Test
        //public void test() throws InterruptedException {
        //
        //    LoadingCache<String, Cpe> loadingCache = CacheBuilder.newBuilder()
        //            //.expireAfterAccess(10, TimeUnit.SECONDS)
        //            .build(new CacheLoader<String, Cpe>() {
        //                public Cpe load(String key) {
        //                    Cpe cpe = queryService.queryCpeByUuid(key);
        //                    return cpe;
        //                }
        //            });
        //
        //    executor.scheduleAtFixedRate(()->{
        //        System.out.println("start");
        //        //List<Cpe> dbCpes = queryService.findAllCpes();
        //        //Map<String, Cpe> cacheCpes = dbCpes.stream().collect(Collectors.toMap(Cpe::getUuid, cpe -> cpe));
        //        //loadingCache.putAll(cacheCpes);
        //    },0,3, TimeUnit.SECONDS);
        //
        //    CountDownLatch countDownLatch = new CountDownLatch(1);
        //
        //    new Thread(new Runnable() {
        //        @Override
        //        public void run() {
        //            try {
        //                countDownLatch.await();
        //            } catch (InterruptedException e) {
        //                e.printStackTrace();
        //            }
        //
        //            for (int i = 0; i <10 ; i++) {
        //                if(i%2==0){
        //                    CpeDO cpeDO = new CpeDO();
        //                    cpeDO.setUuid(System.currentTimeMillis()+"");
        //                    cpeDAO.insert(cpeDO);
        //                }
        //                System.out.println(loadingCache.size());
        //                try {
        //                    Thread.sleep(2000);
        //                    System.out.println(loadingCache.get("70530f98-2298-4071-b1e4-689b9caa82f0"));
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        }
        //    }).start();
        //
        //    Thread.sleep(1000);
        //    countDownLatch.countDown();
        //
        //    Thread.sleep(100000);
        //}
    }

}
