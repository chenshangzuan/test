package kled.test.service;

import api.HelloService;
import common.Result;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author: Kled
 * @version: HelloServiceImp.java, v0.1 2020-11-25 14:46 Kled
 */
@DubboService
public class HelloServiceImp implements HelloService {
    @Override
    public Result<String> hello(String msg) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setData(msg + " handler by provider");
        return result;
    }
}
