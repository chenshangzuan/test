/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test.ventilabisTest;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QcloudSmsSingleSender extends QcloudSmsSender {

    private final SmsSingleSender SMS_SINGLE_SENDER;

    public QcloudSmsSingleSender(String content, int appId, String appKey, String... phoneNumbers) {
        super(content, appId, appKey, phoneNumbers);
        SMS_SINGLE_SENDER = new SmsSingleSender(appId, appKey);
    }

    @Override
    protected void send() throws HTTPException, IOException {
        SMS_SINGLE_SENDER.send(0, "", phoneList.get(0), content, "", "");
    }

    @Override
    protected void sendWithTemplate(Integer templateId, String smsSign, List<String> params) throws HTTPException, IOException {
        SMS_SINGLE_SENDER.sendWithParam("", phoneList.get(0), templateId, (ArrayList<String>) params, smsSign, "", "");
    }
}
