package kled.test.controller;

import api.HelloService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import common.Result;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author: Kled
 * @version: HelloController.java, v0.1 2020-11-25 15:05 Kled
 */
@RestController
@RequestMapping(value = "/test")
public class HelloController {

    @DubboReference(timeout = 5000, version = "1.0.2", stub = "kled.test.service.HelloServiceStub", mock = "true")
    //@DubboReference(timeout = 5000, async = true) //异步, InvokerMode=ASYNC
    private HelloService helloService;

    //同步, InvokerMode=SYNC
    @RequestMapping(value = "hello")
    public String hello(String msg){
        RpcContext.getContext().setAttachment("attachmentKey", "123");
        String response =  helloService.hello(msg).toString();
        return response;
    }

    //异步, InvokerMode=ASYNC
    @RequestMapping(value = "asyncHello1")
    public String asyncHello1(String msg){
        RpcContext.getContext().setAttachment("attachmentKey", "123");
        CompletableFuture<Result<String>> response =  helloService.asyncHello(msg);
        response.whenComplete((ret, throwable) -> {
            if (throwable != null) {
                System.out.println("asyncHello -> request error=" + throwable.getMessage());
            }else {
                System.out.println("asyncHello -> ret=" + ret);
            }
        });
        return "success";
    }

    //异步, InvokerMode=FUTURE
    @RequestMapping(value = "asyncHello2")
    public String asyncHello2(String msg){
        RpcContext.getContext().setAttachment("attachmentKey", "123");
        CompletableFuture<Result<String>> response = RpcContext.getContext().asyncCall(() -> helloService.hello(msg));
        response.whenComplete((ret, throwable) -> {
            if (throwable != null) {
                System.out.println("asyncHello -> request error=" + throwable.getMessage());
            }else {
                System.out.println("asyncHello -> ret=" + ret);
            }
        });
        return "success";
    }
}
