/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import kled.test.bean.ZookeeperFactory;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

public class LeaderElectServiceImpl implements LeaderElectService{

    @Value("${lock.expire.msec}")
    private String mesc ;

    @Autowired
    private ZookeeperFactory zookeeperFactory;

    private boolean isLeader = false;

    @PostConstruct
    public void init() {
        System.out.println(mesc+"*************");

        try {
            tryLeader();
        } catch (Exception e) {

        }
    }

    public boolean isLeader() {
        return isLeader;
    }

    private void tryLeader() throws Exception {
        CuratorFramework client = zookeeperFactory.getObject();
        LeaderLatch latch = new LeaderLatch(client, "/leader");
        latch.addListener(new LeaderLatchListener() {

            @Override
            public void notLeader() {
                System.out.println(latch.getId() + "I'm not the leader");
                isLeader = false;
            }

            @Override
            public void isLeader() {
                System.out.println(latch.getId() + "I'm the leader");
                isLeader = true;
            }
        });
        latch.start();
    }
}
