package api;

import api.HelloService;
import common.Result;

import java.util.concurrent.CompletableFuture;

/**
 * @author: Kled
 * @version: HelloServiceStub.java, v0.1 2020-12-22 10:11 Kled
 */
public class HelloServiceMock implements HelloService {

    @Override
    public Result<String> hello(String msg) {
        System.out.println("HelloServiceMock -> hello, mock is working");
        Result<String> result = new Result<String>();
        result.setSuccess(true);
        result.setData("mock");
        return result;
    }

    @Override
    public CompletableFuture<Result<String>> asyncHello(String msg) {
        System.out.println("HelloServiceStub -> asyncHello, mock is working");
        Result<String> result = new Result<String>();
        result.setSuccess(true);
        result.setData("mock");
        return CompletableFuture.completedFuture(result);
    }
}
