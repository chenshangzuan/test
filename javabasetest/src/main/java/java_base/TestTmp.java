/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package java_base;

import java.sql.Date;
import java.time.Instant;
import java.time.ZoneId;

public class TestTmp {

    public static void main(String[] args) {
        System.out.println(Date.from(Instant.now().atZone(ZoneId.of("UTC")).toLocalDateTime().atZone(ZoneId.of("Asia/Shanghai")).toInstant()));
        System.out.println(Instant.now().atZone(ZoneId.of("Asia/Shanghai")).toLocalDateTime());
    }
}
