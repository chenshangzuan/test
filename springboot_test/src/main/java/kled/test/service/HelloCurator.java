/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import kled.test.bean.ZookeeperFactory;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.RevocationListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloCurator {

    @Autowired
    private ZookeeperFactory zookeeperFactory;

    public void init() {
        try {
            System.out.println("zoooooookeeper:" + zookeeperFactory.getObject().getState());
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) throws Exception {
        //new HelloCurator().testZookeeperConnect();
        new HelloCurator().testLeaderElect();
        //new HelloCurator().testGetChild();
        //new HelloCurator().testLeaderSelector();
        //new HelloCurator().testInterProcessMutex();
    }

    public void testZookeeperConnect() {
        CuratorFramework client = getClient();
        System.out.println("client status：" + client.getState());

        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        String m = client.create().creatingParentContainersIfNeeded().withMode(CreateMode.PERSISTENT)
                                .forPath("/leader-1", "I'm the leader !!!".getBytes());
                        System.out.println(m);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }).start();

        }
    }

    public void testGetChild() throws Exception {
        CuratorFramework client = getClient();
        System.out.println(client.checkExists().forPath("/"));
        List<String> l = null;
        l = client.getChildren().forPath("/");

        for (String s : l) {
            System.out.println(s);
        }
    }

    public void testLeaderElect() throws Exception {
        Map<Integer, LeaderLatch> latches = new HashMap<>();
        for (int i = 0; i < 3; i++) {

            CuratorFramework client = getClient();
            LeaderLatch latch = new LeaderLatch(client, "/leader", "client#" + i);
            //LeaderLatch latch = new LeaderLatch(client, "/leader");
            latch.addListener(new LeaderLatchListener() {

                @Override
                public void notLeader() {
                    System.out.println(latch.getId() + "I'm not the leader");
                }

                @Override
                public void isLeader() {
                    System.out.println(latch.getId() + ":" + System.currentTimeMillis());
                    System.out.println(latch.getId() + "I'm the leader");
                }
            });
            latch.start();
            System.out.println(latch.getId() + "start:" + System.currentTimeMillis());
            //latch.await();//阻塞等待成为Leader -->core leader选举方法
            //latch.await(1, TimeUnit.SECONDS);//阻塞1s等待成为Leader
            Thread.sleep(2000);
            boolean isLeader = latch.hasLeadership(); //内部为原子boolean
            System.out.println(isLeader);
            //            if (i == 2) {
            //                Thread.sleep(500000);
            //            } else {
            //                Thread.sleep(5000);
            //            }
            // latch.close();`
            latches.put(i, latch);
        }
        //latches.get(0).close();//leader crash
        //System.out.println("leader switch:" + System.currentTimeMillis());
        Thread.sleep(10000000);
    }

    public void testInterProcessMutex() throws Exception {
        InterProcessMutex lock = new InterProcessMutex(getClient(), "/lock");
        ExecutorService executor = Executors.newFixedThreadPool(5);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        for (int i = 0; i < 5; i++) {
            executor.execute(new Runnable() {

                @Override
                public void run() {
                    int clientId = atomicInteger.getAndIncrement();
                    System.out.println(
                            "client" + clientId + " try lock:" + System.currentTimeMillis());
                    try {
                        lock.makeRevocable(new RevocationListener<InterProcessMutex>() {

                            @Override
                            public void revocationRequested(InterProcessMutex forLock) {
                                //锁可撤销机制
                            }
                        });
                        //Revoker.attemptRevoke(getClient(), "/lock"); 撤销请求
                        if (!lock.acquire(10, TimeUnit.SECONDS)) {
                        throw new IllegalStateException(" could not acquire the lock");
                        }
                        System.out.println("client" + clientId + " get lock:" + System.currentTimeMillis());
                        Thread.sleep(3000);
                        System.out.println("client" + clientId + " release lock start:" + System.currentTimeMillis());
                        lock.release();
                        System.out.println("client" + clientId + " release lock end:" + System.currentTimeMillis());
                    } catch (Exception e) {

                    }
                }
            });
            Thread.sleep(1000);
        }
    }

    private void testLeaderSelector() throws IOException {
        for (int i = 0; i < 2; i++) {
            CuratorFramework client = getClient();
            LeaderSelectorAdapter leaderSelectorAdapter = new HelloCurator.LeaderSelectorAdapter(client,
                    "/leaderSelector", "client#" + i);
            leaderSelectorAdapter.start();
        }
    }

    private CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("172.16.0.216:2181,172.16.0.215:2181,172.16.0.212:2181")
                .sessionTimeoutMs(720).connectionTimeoutMs(500).retryPolicy(retryPolicy).namespace("test").build();
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {

            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {
                //连接监听
                System.out.println("######################" + newState.name());
            }
        });
        client.start();
        return client;
    }

    private static class LeaderSelectorAdapter extends LeaderSelectorListenerAdapter implements Closeable {

        private final String name;
        private final LeaderSelector leaderSelector;
        private final AtomicInteger leaderCount = new AtomicInteger();

        public LeaderSelectorAdapter(CuratorFramework client, String path, String name) {
            this.name = name;
            leaderSelector = new LeaderSelector(client, path, this);
            leaderSelector.autoRequeue();
        }

        public void start() throws IOException {
            leaderSelector.start();
        }

        @Override
        public void close() throws IOException {
            leaderSelector.close();
        }

        @Override
        public void takeLeadership(CuratorFramework client) throws Exception {
            final int waitSeconds = (int) (5 * Math.random()) + 1;
            System.out.println(name + " is now the leader. Waiting " + waitSeconds + " seconds...");
            System.out.println(name + " has been leader " + leaderCount.getAndIncrement() + " time(s) before.");
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(waitSeconds));
            } catch (InterruptedException e) {
                System.err.println(name + " was interrupted.");
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(name + " relinquishing leadership.\n");
            }
        }
    }
}
