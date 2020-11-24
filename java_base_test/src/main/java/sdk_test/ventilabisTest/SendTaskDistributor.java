/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test.ventilabisTest;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SendTaskDistributor {

    private final LinkedBlockingQueue<SendTask> smsQueue = new LinkedBlockingQueue<>();

    private final Executor smsMessageExecutor = Executors.newFixedThreadPool(10);

    public void init() {
        startSmsMessageConsumer();
    }

    private void startSmsMessageConsumer() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    SendTask smsTask = smsQueue.take();
                    smsMessageExecutor.execute(() -> {
                        //do sms send
                        QcloudSmsSender qcloudSmsSender = new QcloudSmsSender("", 0, "",  "");
                        try {
                            qcloudSmsSender.send();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
    }

    public void distributeTask(MQMessage message) throws InterruptedException {
        //1. save task to db （convert message to send task）
        //2. 渲染内容
        //3. distribute task to correct queue
        String str = "SMS";
        switch (str) {
            case "SMS":
                smsQueue.put(new SendTask());
                break;
            default:
                break;
        }
    }
}
