package kled.test.controller.mvc_test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Kled
 * @version: ErrorController.java, v0.1 2020-11-13 11:53 Kled
 */
@Controller("/test")
public class ErrorController {

    @GetMapping(value = "/errorPage")
    public String errorPage(){
        return "error";
    }
}
