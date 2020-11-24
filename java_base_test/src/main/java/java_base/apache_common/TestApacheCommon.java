package java_base.apache_common;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author chenpc
 * @version $Id: java_base.apache_common.TestApacheCommon.java, v 0.1 2018-05-04 17:18:24 chenpc Exp $
 */

public class TestApacheCommon {
    public static void main(String[] args) throws InterruptedException{
        //testStopWatch();

//        List<String> list = Lists.newArrayList();
//        SetUniqueList<String> sl = SetUniqueList.decorate(list);  //去重list
//        sl.add("S");
//        sl.add("S");
//        sl.add("L");
//        sl.forEach(System.out::println);
//
//        //SynchronizedList 线程安全的list
//        //MultiMap <--> MultiValueMap
//
//        MultiKeyMap keyMap = new MultiKeyMap();
//
//        keyMap.put("a","b","c");
//        System.out.println(keyMap.get("ab"));

        //ListUtils/CollectionUtils intersection|subtract|union|disjunction(不相交并集)|containsAny  -->不改变原集合
    }

    private static void testStopWatch() throws InterruptedException{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Thread.sleep(1000);
        stopWatch.split();
        System.out.println("split time one:"+stopWatch.getSplitTime());
        Thread.sleep(1000);
        stopWatch.split();
        System.out.println("split time two:"+stopWatch.getSplitTime());
        stopWatch.stop();

        //1. start：启动一个时间会话，会清除以前所有的数据
        //2. stop：停止一个时间会话
        //3. reset：清除数据，以便对象重用
        //4. split：设置一个停止时间，以便可以提取运行时间，但是不影响启动时间，以便最终的时间记录正确  -->记录分割时间点
        //5. unsplit：重置停止时间，启动时间不受影响，移除split状态
        //6. suspend：暂停，直到resume方法调用，最终将不计算suspend和resume之间的时间!!!!
        //7. resume：恢复暂停状态
    }
}
