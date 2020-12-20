package kled.test.controller;

import api.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Kled
 * @version: HelloController.java, v0.1 2020-12-14 17:06 Kled
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(){
        return "hello!";
    }
}
