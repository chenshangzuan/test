/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package sdk_test;

import com.github.qcloudsms.SmsVoicePromptSender;
import com.github.qcloudsms.SmsVoicePromptSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

public class QcloudVoiceTest {

    // 短信应用 SDK AppID
    private static int appid = 1400229580; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    private static String appkey = "f7300add9766d3933fcd725ec7f15b6f";
    // 需要发送短信的手机号码
    private static String phoneNumber = "18758237407";

    public static void main(String[] args) {
        SmsVoicePromptSender vpsender = new SmsVoicePromptSender(appid, appkey);
        SmsVoicePromptSenderResult result = null;
        try {
            result = vpsender.send("86", phoneNumber,
                    2, 2, "这是一条测试语音", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
