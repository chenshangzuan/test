/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test.ventilabisTest;

import com.github.qcloudsms.httpclient.HTTPException;
import com.google.common.collect.Lists;

import java.io.IOException;

public class MySmsSenderTest {

    public static void main(String[] args) throws HTTPException, IOException {
        QcloudSmsSender qcloudSmsSender = new QcloudSmsSender("",1,"");
        qcloudSmsSender.sendWithTemplate(1,"", Lists.newArrayList());
    }
}
