/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test.ventilabisTest;

import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractSmsSender extends SenderBase{

    private int appId;

    private String appKey;

    protected List<String> phoneList;

    AbstractSmsSender(String content, int appId, String appKey, String... phoneNumbers) {
        super(content);
        this.appId = appId;
        this.appKey = appKey;
        this.phoneList = Arrays.asList(phoneNumbers);
    }

    protected abstract void send() throws HTTPException, IOException;

    protected abstract void sendWithTemplate(Integer templateId, String smsSign, List<String> params) throws HTTPException, IOException;
}
