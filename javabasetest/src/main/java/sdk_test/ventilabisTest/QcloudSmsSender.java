/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test.ventilabisTest;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.List;

public class QcloudSmsSender extends AbstractSmsSender {

    private QcloudSmsSender qcloudSmsSender;

    QcloudSmsSender(String content, int appId, String appKey, String... phoneNumbers) {
        super(content, appId, appKey, phoneNumbers);
        if(phoneList.size() > 1){
            qcloudSmsSender = new QcloudSmsMultiSender(content, appId, appKey, phoneNumbers);
        }else {
            qcloudSmsSender = new QcloudSmsSingleSender(content, appId, appKey, phoneNumbers);
        }
    }

    @Override
    protected void send() throws HTTPException, IOException {
        qcloudSmsSender.send();
    }

    @Override
    protected void sendWithTemplate(Integer templateId, String smsSign, List<String> params) throws HTTPException, IOException {
        qcloudSmsSender.sendWithTemplate(templateId, smsSign, params);
    }
}
