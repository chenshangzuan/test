/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */

import java.lang.instrument.Instrumentation;

/**
 * @author: Kled
 * @version: PreMainAgent.java, v0.1 2020-09-29 17:28 Kled
 */
public class PreMainAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("agentArgs : " + agentArgs);
    }
}
