/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package java_base.j2ee;


import com.sun.tools.attach.VirtualMachine;

/**
 * @author: Kled
 * @version: TestJavaAgentInject.java, v0.1 2020-09-29 17:46 Kled
 */
public class TestJavaAgentInject {

    public static void main(String[] args) {
        try {
            //主程序运行后代理
            VirtualMachine vm = VirtualMachine.attach("26450"); //目标jvm进程pid
            vm.loadAgent("/Users/kled/git/test/javaagenttest/target/java.agent.test-2.4.jar");
            vm.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
