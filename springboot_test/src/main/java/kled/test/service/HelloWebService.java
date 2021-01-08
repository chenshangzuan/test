/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface HelloWebService {
    @WebMethod
    public String sayHello();
}
