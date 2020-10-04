/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test;

import com.github.qcloudsms.*;
import com.github.qcloudsms.httpclient.HTTPException;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class QcloudSmsTest {

    private static int templateId = 371840; //已审核通过的模板ID

    private static String smsSign = "若木之瑟"; //签名的内容

    private static SmsSingleSender ssender = new SmsSingleSender(QcloudUserContant.APP_ID, QcloudUserContant.APP_KEY);

    public static void main(String[] args) throws HTTPException, IOException {
        QcloudSmsTest qcloudSmsTest = new QcloudSmsTest();
        //qcloudSmsTest.testSendSms();

        //qcloudSmsTest.testSendSmsWithTemplate();

        //短信模板添加获取SIG
//        StringBuffer buffer = (new StringBuffer("appkey=")).append(appkey).append("&random=").append(2).append("&time=").append(1562852225);
//        System.out.println(DigestUtils.sha256Hex(buffer.toString()));
    }

    private void testSendSms() throws HTTPException, IOException {
        SmsSingleSenderResult result = ssender.send(0, "86", QcloudUserContant.PHONE_NUMBERS[0],
                "测试短信", "", "");
        System.out.println(result);
    }

    private void testSendSmsWithTemplate() throws HTTPException, IOException {
        String[] params = {"5678", "1"};
        SmsSingleSenderResult result = ssender.sendWithParam("86", QcloudUserContant.PHONE_NUMBERS[0],
                templateId, params, smsSign, "", "");
        System.out.println(result);
    }

}
