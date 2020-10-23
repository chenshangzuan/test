package java_base;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class TestConcurrent {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
       
//        BlockingQueue<String> queue = new LinkedBlockingQueue<>();
//        for(int i=0;i<3;i++){
//            queue.put(""+i);   
//        }
//        List<String> lists = new ArrayList<>();
//        queue.drainTo(lists, 3);
//        for(int i=0 ;i<3;i++){
//           System.out.println(lists.get(i));
//        }
//        
//        System.out.println(queue.isEmpty());
        
//        List<java_base.ReportEvent> lists = new LinkedList<>();
//        lists.add(new java_base.ReportEvent("port", 1,2020));
//        lists.add(new java_base.ReportEvent("port", 1,2022));
//        lists.add(new java_base.ReportEvent("port", 1,2024));
//        Collections.reverse(lists);
//        Set<java_base.ReportEvent> reportEvents = new HashSet<>();
//        reportEvents.addAll(lists);
//        Iterator<java_base.ReportEvent> iterator = reportEvents.iterator();
//        java_base.ReportEvent reportEvent ;
//        System.out.println(reportEvents.size());
//        while(iterator.hasNext()){
//            reportEvent = iterator.next();
//            System.out.println(reportEvent.getTsms());
//        }
        // ConcurrentMap 写入时间测试实验

        //        ConcurrentMap<Integer,String> testMap = new ConcurrentHashMap<>();
        //        CountDownLatch countDownLatch = new CountDownLatch(1000); 
        //        ThreadPoolExecutor executor = new ThreadPoolExecutor(1000,1000,10,TimeUnit.SECONDS,new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.DiscardOldestPolicy());
        //        long timeConsume = 0;
        //        for (int i = 0; i < 1000; i++) {
        //            System.out.println("Start Thread "+i);
        //            Future<Long> cost = executor.submit(new Callable<Long>() {
        //                @Override
        //                public Long call() throws Exception {
        //                    countDownLatch.countDown();
        //                    long start = System.currentTimeMillis();
        //                    //System.out.println((int)(Math.random()*1000));
        //                    // 十万次写入Concurrent_Map
        //                    for (int i = 0; i < 1000; i++) {
        //                        testMap.put((int)(Math.random()*1000), "port");
        //                    }
        //                    long end = System.currentTimeMillis();
        //                    return end-start;
        //                }
        //            });
        //            timeConsume+=cost.get();
        //            System.out.println("task "+i+" cost :"+cost.get());
        //        }
        //        countDownLatch.await();
        //        System.out.println("Map_size :"+testMap.size());
        //        System.out.println(timeConsume);
        //        executor.shutdown();

        //Map<String, Integer> map = new HashMap<>();
        //map.put("test", 1);
        //Integer i = map.get("test");
        //i = i + 1;
        //System.out.println(map.get("test"));

        List<Integer> aa = null;
        for(Integer i : aa){
            System.out.println("a");
        }

    }

}
