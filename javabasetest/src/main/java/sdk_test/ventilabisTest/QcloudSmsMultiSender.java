/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test.ventilabisTest;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QcloudSmsMultiSender extends QcloudSmsSender {

    private final SmsMultiSender SMS_MULTI_SENDER;

    public QcloudSmsMultiSender(String content, int appId, String appKey, String... phoneNumbers) {
        super(content, appId, appKey, phoneNumbers);
        SMS_MULTI_SENDER = new SmsMultiSender(appId, appKey);
    }

    @Override
    protected void send() throws HTTPException, IOException {
        SMS_MULTI_SENDER.send(0, "", (ArrayList<String>) phoneList, content, "", "");
    }

    @Override
    protected void sendWithTemplate(Integer templateId, String smsSign, List<String> params) throws HTTPException, IOException {
        SMS_MULTI_SENDER.sendWithParam("", (ArrayList<String>)phoneList, templateId, (ArrayList<String>) params, smsSign, "", "");
    }
}
