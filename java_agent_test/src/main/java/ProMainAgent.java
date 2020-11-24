/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */

import java.lang.instrument.Instrumentation;

/**
 * @author: Kled
 * @version: PreMainAgent.java, v0.1 2020-09-29 17:28 Kled
 */
public class ProMainAgent {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain : " + agentArgs);

        Class<?>[] classes = inst.getAllLoadedClasses();
        for (Class<?> aClass : classes) {
            System.out.println(aClass.getName());
        }
        System.out.println("agentmain : run completely");
    }
}
