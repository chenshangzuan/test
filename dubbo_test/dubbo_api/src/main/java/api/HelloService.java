package api;

import common.Result;

/**
 * @author: Kled
 * @version: HelloService.java, v0.1 2020-11-24 22:01 Kled
 */
public interface HelloService {

    Result<String> hello(String msg);
}
