/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import kled.test.bean.ZookeeperFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class DistributedResourceLockServiceImpl {

    @Autowired
    private ZookeeperFactory zookeeperFactory;

    public void acquireVllLock(int vllId) throws Exception {
        if (!getVllLock(vllId).acquire(1000, TimeUnit.MILLISECONDS)) {
            throw new IllegalStateException(" could not acquire the vll lock");
        }
    }

    public void aquireAndWaitVllLock(int vllId) throws Exception {
        getVllLock(vllId).acquire();
    }

    public void releaseVllLock(int vllId) throws Exception {
        getVllLock(vllId).release();
    }

    public void acquireResourceLock() throws Exception {
        if (!getResouceLock().acquire(1000, TimeUnit.MILLISECONDS)) {
            throw new IllegalStateException(" could not acquire the lock");
        }
    }

    public void aquireAndWaitResourceLock() throws Exception {
        getResouceLock().acquire();
    }

    public void releaseResourceLock() throws Exception {
        getResouceLock().release();
    }

    private InterProcessMutex getVllLock(int vllId) throws Exception {
        InterProcessMutex vllLock = new InterProcessMutex(zookeeperFactory.getObject(), "/lock" + vllId);
        return vllLock;
    }

    private InterProcessSemaphoreMutex getResouceLock() throws Exception {
        InterProcessSemaphoreMutex resourceLock = new InterProcessSemaphoreMutex(zookeeperFactory.getObject(),
                "/resource");
        return resourceLock;
    }
}
