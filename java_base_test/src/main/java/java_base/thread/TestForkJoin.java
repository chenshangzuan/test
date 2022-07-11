package java_base.thread;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

import java.util.concurrent.*;

/**
 * @author chenpc
 * @version $Id: java_base.thread.TestForkJoin.java, v 0.1 2018-02-28 10:56:29 chenpc Exp $
 */
public class TestForkJoin {

    public static void main(String[] args) {
        //ExecutorService executorService = Executors.newWorkStealingPool();
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTask forkJoinTask = new ForkJoinTask();
        forkJoinPool.invoke(forkJoinTask);
        forkJoinPool.submit(forkJoinTask);
        forkJoinPool.execute(forkJoinTask);
        forkJoinTask.fork();
    }

    static class ForkJoinTask extends RecursiveTask<Long> {

        @Override
        protected Long compute() {
            return null;
        }
    }
}
