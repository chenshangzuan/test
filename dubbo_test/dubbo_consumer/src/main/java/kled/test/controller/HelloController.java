package kled.test.controller;

import api.HelloService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Kled
 * @version: HelloController.java, v0.1 2020-11-25 15:05 Kled
 */
@RestController
@RequestMapping(value = "/test")
public class HelloController {

    @DubboReference
    private HelloService helloService;

    @RequestMapping(value = "hello")
    public String hello(String msg){
        return helloService.hello(msg).toString();
    }
}
