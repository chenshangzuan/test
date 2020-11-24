/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Value;

public class ZookeeperFactory implements FactoryBean<CuratorFramework> {

    @Value("${spring.zookeeper.connectString}")
    private String connectString = "172.16.0.212:2181,172.16.0.215:2181,172.16.0.216:2181";

    @Value("${spring.zookeeper.sessionTimeoutMs}")
    private int sessionTimeoutMs = 300;

    @Value("${spring.zookeeper.connectionTimeoutMs}")
    private int connectionTimeoutMs = 200;

    @Value("${spring.zookeeper.namespace}")
    private String root = "core";

    private CuratorFramework zkClient;

    private boolean isSingleton = false;

    @Override
    public CuratorFramework getObject() throws Exception {
        if (isSingleton) {
            if (zkClient == null) {
                zkClient = create();
                zkClient.start();
            }
            return zkClient;
        }
        return create();
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }

    @Override
    public boolean isSingleton() {
        return isSingleton;
    }

    private CuratorFramework create(){
        CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();  
        return builder.connectString(connectString).sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs).namespace(root)
                .retryPolicy(new ExponentialBackoffRetry(100, 3)).build();
    }
}
