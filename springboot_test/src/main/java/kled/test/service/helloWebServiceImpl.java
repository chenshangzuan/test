/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import javax.jws.WebService;

@WebService(targetNamespace = "http://spring_test/", endpointInterface = "spring_test.HelloWebService")
public class helloWebServiceImpl implements HelloWebService {

    @Override
    public String sayHello() {
        return "hello web services";
    }

}
