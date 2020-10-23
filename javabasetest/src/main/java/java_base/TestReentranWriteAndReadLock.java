package java_base;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReentranWriteAndReadLock {

    private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private static Lock rl = rwl.readLock();
    private static Lock wl = rwl.writeLock();

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    //writeJob();
                    //readJob();
                    //writeThenRead();
                    readThenWrite();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                // writeJob();
                //readJob();
            }
        }).start();
    }

    public static void writeJob() throws InterruptedException {
        wl.lock();
        try {
            System.out.println("writeLock locked:" + Thread.currentThread().getName());
            Thread.sleep(2000);
        } finally {
            wl.unlock();
            System.out.println("writeLock release:" + Thread.currentThread().getName());
        }
    }

    public static void readJob() throws InterruptedException {
        rl.lock();
        try {
            System.out.println("readLock locked:" + Thread.currentThread().getName());
            Thread.sleep(2000);
        } finally {
            rl.unlock();
            System.out.println("readLock release:" + Thread.currentThread().getName());
        }
    }

    public static void writeThenRead() throws InterruptedException {
        //当前线程写锁升级，成功获得读锁
        wl.lock();
        try {
            System.out.println("writeLock locked:" + Thread.currentThread().getName());
            readJob();
            System.out.println("reenTrantReadWrite lock:" + Thread.currentThread().getName());
            Thread.sleep(2000);
        } finally {
            wl.unlock();
            System.out.println("writeLock release:" + Thread.currentThread().getName());
        }
    }

    public static void readThenWrite() throws InterruptedException {
        //当前线程获得读锁，无法重入写锁，阻塞
        rl.lock();
        try {
            System.out.println("readLock locked:" + Thread.currentThread().getName());
            try {
                wl.tryLock(5000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
            }
            System.out.println("read then write fail");
            Thread.sleep(2000);
        } finally {
            rl.unlock();
            System.out.println("readLock release:" + Thread.currentThread().getName());
        }
    }
}
