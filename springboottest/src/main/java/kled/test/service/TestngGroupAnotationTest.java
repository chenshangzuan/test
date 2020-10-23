/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import org.testng.annotations.Test;

@Test(groups = "test")
public class TestngGroupAnotationTest {

    //@java_base.Test(dependsOnGroups = "test", groups = "testMethod")
    @Test(groups = "testMethod")
    public void test1() {
        System.out.println("test group");
    }

}
