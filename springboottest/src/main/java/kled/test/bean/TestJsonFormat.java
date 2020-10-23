/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author chenpc
 * @version $Id: java_base.TestJsonFormat.java, v 0.1 2018-03-23 11:52:01 chenpc Exp $
 */
public class TestJsonFormat {

    @DateTimeFormat(pattern = "yyyy-MM-dd")  //符合该模式，则转化为Date
    private Date dateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")  //如果不添加会直接返回时间戳
    private Date currentTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getCurrentTime() {
        currentTime = new Date();
        return currentTime;
    }
}
