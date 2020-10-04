/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package localTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TestExecutor {
    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        ExecutorService executor1 = Executors.newFixedThreadPool(10);
        ExecutorService executor2 = Executors.newSingleThreadExecutor();
        ExecutorService executor3 = Executors.newCachedThreadPool();
    }
}
