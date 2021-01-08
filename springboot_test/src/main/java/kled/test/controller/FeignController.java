/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.controller;

import kled.test.bean.HelloRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.TimeZone;

@RestController
@RequestMapping(value = "/feign")
public class FeignController {

    @RequestMapping(value = "/test", method = RequestMethod.GET, produces = "application/json")
    public String test1(HttpServletRequest request,String msg, TimeZone timeZone) {
        return msg;
    }

    @PostMapping(value = "/test", produces = "application/json")
    public String test2(@RequestBody HelloRequest request) {
        return request.getMsg();
    }
}
