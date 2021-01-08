package kled.test.service;

import api.HelloService;
import common.Result;

import java.util.concurrent.CompletableFuture;

/**
 * @author: Kled
 * @version: HelloServiceStub.java, v0.1 2020-12-22 10:11 Kled
 */
public class HelloServiceStub implements HelloService {

    private HelloService helloService;

    public HelloServiceStub(HelloService helloService) {
        this.helloService = helloService;
    }

    @Override
    public Result<String> hello(String msg) {
        System.out.println("HelloServiceStub -> hello, stub is working");
        return helloService.hello(msg);
    }

    @Override
    public CompletableFuture<Result<String>> asyncHello(String msg) {
        System.out.println("HelloServiceStub -> asyncHello, stub is working");
        return helloService.asyncHello(msg);
    }
}
