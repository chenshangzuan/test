package localTest;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author chenpc
 * @version $Id: localTest.TestForkJoin.java, v 0.1 2018-02-28 10:56:29 chenpc Exp $
 */
public class TestForkJoin {

    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask forkJoinTask = new ForkJoinTask();
        forkJoinPool.invoke(forkJoinTask);
        forkJoinTask.fork();
    }

    static class ForkJoinTask extends RecursiveTask<Long> {

        @Override
        protected Long compute() {
            return null;
        }
    }
}
