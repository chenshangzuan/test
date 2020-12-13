package kled.test.controller;

import api.HelloRequest;
import api.HelloResponse;
import api.HelloWorldGrpc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Kled
 * @version: DemoController.java, v0.1 2020-12-12 18:50 Kled
 */
@RestController
public class DemoController {

    @Autowired
    private HelloWorldGrpc.HelloWorldBlockingStub helloWorldBlockingStub;

    @GetMapping(value = "/hello", produces = "application/json;charset=UTF-8")
    public String hello(@RequestParam String param){
        HelloRequest helloRequest = HelloRequest.newBuilder().setMsg(param).build();
        HelloResponse response = helloWorldBlockingStub.hello(helloRequest);
        System.out.println("DemoController -> response=" + response);
        return response.getMsg();
    }
}
