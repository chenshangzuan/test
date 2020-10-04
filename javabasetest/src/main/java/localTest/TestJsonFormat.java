package localTest;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author chenpc
 * @version $Id: localTest.TestJsonFormat.java, v 0.1 2018-03-23 11:52:01 chenpc Exp $
 */
public class TestJsonFormat {

    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
