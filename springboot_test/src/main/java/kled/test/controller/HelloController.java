/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2020 All Rights Reserved.
 */
package kled.test.controller;

import com.fabric4cloud.oxygen.common.model.ResultInfo;
import com.google.common.collect.Lists;
import io.github.yedaxia.apidocs.ApiDoc;
import kled.test.bean.HelloRequest;
import kled.test.bean.ImportBeanA;
import kled.test.bean.ImportBeanB;
import kled.test.bean.ImportBeanC;
import kled.test.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

/**
 * Hello接口
 */
@RestController
@RequestMapping(value = "/test")
public class HelloController {

    @Autowired
    private ImportBeanA importBeanA;

    @Autowired
    private ImportBeanB importBeanB;

    @Autowired
    private ImportBeanC importBeanC;

    @RequestMapping(value = "/httpSession", method = RequestMethod.GET)
    public String testSession(HttpServletRequest request) {

        HttpSession httpSession = request.getSession(false);
        System.out.println("httpSession=" + httpSession);
        //手动创建session, 存于内存中
        request.getSession(true).setAttribute("sessionId", "kled");
        return "success";
    }

    /**
     * dasda
     * @param request
     * @return
     */
    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String getSession(HttpServletRequest request) {
        Arrays.asList(request.getCookies()).forEach(x-> {
            System.out.println(x.getName() + ":" + x.getValue());
        });
        //测试Session域共享
        return (String) request.getSession().getAttribute("sessionId");
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json")
    public ResultInfo<String> version(HttpServletRequest request,String hello, TimeZone timeZone) {
        System.out.println(timeZone);
        System.out.println(request.getContextPath());  //web 根目录, URI-Servlet
        System.out.println(request.getServletPath());  //RequestMapper 地址
        System.out.println(request.getRequestURI());   //除了IP+PORT外的 URL
        System.out.println(request.getRequestURL());
        System.out.println(request.getQueryString());  //查询条件
        ResultInfo<String> resultInfo = new ResultInfo<>();
        return resultInfo.succeed().withData("hello!");
    }

    @PostMapping(value = "/hello", produces = "application/json")
    public ResultInfo<String> version(HttpEntity<HelloRequest> request) {
        ResultInfo<String> resultInfo = new ResultInfo<>();
        return resultInfo.succeed().withData(request.getBody().getMsg());
    }

    @PostMapping(value = "/httpEntity", produces = "application/json")
    public ResultInfo<String> version(HelloRequest request) {
        ResultInfo<String> resultInfo = new ResultInfo<>();
        return resultInfo.succeed().withData(request.getMsg());
    }

    @GetMapping(value = "/import", produces = "application/json")
    public ResultInfo<String> version() {
        ResultInfo<String> resultInfo = new ResultInfo<>();
        List<String> importBeans = Lists.newArrayList(importBeanA.toString(), importBeanB.toString(), importBeanC.toString());
        return resultInfo.succeed().withData(importBeans.toString());
    }

    @RequestMapping(value = "/aop", method = RequestMethod.GET)
    public String testAspect() {
        System.out.println("hello spring aop");
        //throw new NullPointerException();
        return "hello spring aop";
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public void testError(HttpServletResponse response) throws MyException {
        throw new MyException();
    }

    //@RequestMapping(value = "/testJsonFormat", method = RequestMethod.GET)
//    public TestJsonFormat testJsonFormat(TestJsonFormat testJsonFormat) {
//        System.out.println(testJsonFormat.getDateTime());
//        return testJsonFormat;
//    }
}
