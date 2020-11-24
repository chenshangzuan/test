/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void send() {
        SimpleMailMessage message = new SimpleMailMessage();
        //message.setFrom("Test<junhu.hao@zenlayer.com>");
        message.setFrom("<junhu.hao@zenlayer.com>");
        message.setSubject("测试邮件");
        message.setText("112");
        //message.setTo("shangzuanchen@126.com");
        message.setTo("kled.chen@zenlayer.com");
        javaMailSender.send(message);

    }
}
