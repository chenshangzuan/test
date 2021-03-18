/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kled.test;

import org.junit.Test;
import org.redisson.api.*;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.StatusListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.IdGenerator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

/**
 * Tests for {@link RedisSpringBootApplication}.
 * 
 * @author Dave Syer
 */
public class RedissonTests extends SpringTestRedisSpringBootApplicationTests {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testValue(){
        RBucket<String> bucket = redissonClient.getBucket("name");
        bucket.set("zhaoyun");
    }

    @Test
    public void testList(){
        RList<String> rList = redissonClient.getList("list");
        rList.add("999");
    }

    @Test
    public void testSet(){
        RSet<String> rset = redissonClient.getSet("set");
        rset.add("2021");
    }

    @Test
    public void testSortedSet(){
        RSortedSet<String> zset = redissonClient.getSortedSet("zset");
        zset.add("2222");
    }

    //********************* 分布式锁 **********************//
    @Test
    public void testLock(){
        RLock rLock = redissonClient.getLock("redLock");
        rLock.lock();
        System.out.println("get redLock");
        rLock.unlock();
        System.out.println("release redLock");
    }

    @Test
    public void testRWLock() throws InterruptedException {
        ReadWriteLock readWriteLock = redissonClient.getReadWriteLock("rwLock");
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.readLock();
        new Thread(() -> {
            readLock.tryLock();
            System.out.println("thread 1 get readLock");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readLock.unlock();
        }).start();

        new Thread(() -> {
            readLock.tryLock();
            System.out.println("thread 2 get readLock");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readLock.unlock();
        }).start();

        new Thread(() -> {
            writeLock.tryLock();
            System.out.println("thread 3 get writeLock");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(2000);
    }

    @Test
    public void testCountDown() throws InterruptedException {
        RCountDownLatch rCountDownLatch = redissonClient.getCountDownLatch("RLatch");
        rCountDownLatch.trySetCount(1);

        new Thread(() -> {
            try {
                System.out.println("thread 1 is await");
                rCountDownLatch.await();
                System.out.println("thread 1 is awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("thread 2 is await");
                rCountDownLatch.await();
                System.out.println("thread 2 is awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);
        System.out.println("count down");
        rCountDownLatch.countDown();

        Thread.sleep(1000);
    }

    //********************* 原子基本数据类型操作 **********************//
    @Test
    public void testAtomicData(){
        RAtomicLong rAtomicLong = redissonClient.getAtomicLong("MyId");
        System.out.println(rAtomicLong.incrementAndGet());

        //LongAdder对象，为分布式环境下递增和递减操作提供了很高得性能
        //比分布式AtomicLong对象快12000倍
        RLongAdder atomicLong = redissonClient.getLongAdder("myLongAdder");
        atomicLong.add(12);
        atomicLong.increment();
        atomicLong.decrement();
        atomicLong.sum();
    }

    //********************* 主键自增 **********************//
    @Test
    public void testIdGenerator(){
        RIdGenerator idGenerator = redissonClient.getIdGenerator("MyIdGenerator");
        for (int i = 0; i < 5; i++) {
            System.out.println(idGenerator.nextId());
        }
    }

    //********************* 限流 **********************//
    @Test
    public void testRateLimit() throws InterruptedException {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter("myRateLimiter");
        //两分钟之内最多只有5个线程在执行
        rRateLimiter.trySetRate(RateType.PER_CLIENT,5,2, RateIntervalUnit.MINUTES);

        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i=0;i<10;i++){
            executorService.submit(()->{
                try{
                    //获取令牌
                    rRateLimiter.acquire();
                    System.out.println("线程"+Thread.currentThread().getId()+"进入数据区："+System.currentTimeMillis());
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        Thread.sleep(3000);
    }

    //********************* 缓存 **********************//
    @Test
    public void TestMapCache(){
        RMapCache<String, String> rMapCache = redissonClient.getMapCache("MyMapCache");
        rMapCache.put("cacheKey", "cacheValue", 1, TimeUnit.SECONDS);
        rMapCache.fastPut("cacheKey", "cacheValue");
        System.out.println(rMapCache.getOrDefault("cacheKey", "defaultValue"));
    }
    //********************* 发布订阅 Topic**********************//

    @Test
    public void testTopic() throws InterruptedException {
        RTopic topic = redissonClient.getTopic("topic1");
        System.out.println("listener count=" + topic.countListeners());
        System.out.println("subscribers count=" + topic.countSubscribers());
        System.out.println("channelNames=" + topic.getChannelNames());
        topic.publish("hello redis pub/sub by redisson");
        Thread.sleep(5000);
    }
    //********************* 队列 Queue/Deque **********************//
    @Test
    public void testQueue() throws InterruptedException {
        RBlockingQueue<String> blockingQueue = redissonClient.getBlockingQueue("queue1");
        blockingQueue.add("hello redis queue");

        RDelayedQueue<String> delayedQueue = redissonClient.getDelayedQueue(blockingQueue);
        delayedQueue.offer("hello redis delay queue", 2, TimeUnit.SECONDS);
        System.out.println("wait for delay msg ");
        Thread.sleep(5000);
    }

    //***************** BloomFilter 布隆过滤器************
    //BloomFilter是一种空间效率的概率型数据结构，由Burton Howard Bloom 1970年提出的。
    //通常用来判断一个元素是否在集合中。具有极高的空间效率，但是会带来假阳性(False positive)的错误。
    @Test
    public void testBloomFilter(){
        RBloomFilter<String> bloomFilter = redissonClient.getBloomFilter("sample");
        //初始化布隆过滤器，预计统计元素数量为55000000，期望误差率为0.03
        bloomFilter.tryInit(55000000L, 0.03);
        //根据Hash Code存与Bit中
        bloomFilter.add("field1Value");
        bloomFilter.add("field5Value");
        bloomFilter.contains("field1Value");
    }
}
