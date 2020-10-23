/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2019 All Rights Reserved.
 */
package java_base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestSmsTemplateRegex {

    private static final Pattern QCLOUD_SMS_TEMPLATE_PARAM_PATTERN = Pattern.compile("\\{[A-Z]*\\}?");

    private static final String FRONT_BRACE = "{";

    private static final String POST_BRACE = "}";

    public static void main(String[] args) {

        String ms = "xxxx{CPE}, xxxxxxxxx{SITE}, xxxxxxxx{POLICY}xxxx, xxxx{CPE}";

        Matcher matcher = QCLOUD_SMS_TEMPLATE_PARAM_PATTERN.matcher(ms);

        StringBuffer sb = new StringBuffer();

        Integer index = 1;
        while (matcher.find()){
            matcher.appendReplacement(sb, FRONT_BRACE + index.toString() + POST_BRACE);
            index ++;
        }
        matcher.appendTail(sb);
        System.out.println(sb.toString());
    }
}
