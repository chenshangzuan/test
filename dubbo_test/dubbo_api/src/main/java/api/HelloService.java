package api;

import common.Result;

import java.util.concurrent.CompletableFuture;

/**
 * @author: Kled
 * @version: HelloService.java, v0.1 2020-11-24 22:01 Kled
 */
public interface HelloService {

    Result<String> hello(String msg);

    CompletableFuture<Result<String>> asyncHello(String msg);
}
