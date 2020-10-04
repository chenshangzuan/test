/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import javax.jws.WebService;

@WebService(targetNamespace = "http://spring_test/", endpointInterface = "spring_test.HelloService")
public class helloServiceImpl implements HelloService {

    @Override
    public String sayHello() {
        return "hello web services";
    }

}
