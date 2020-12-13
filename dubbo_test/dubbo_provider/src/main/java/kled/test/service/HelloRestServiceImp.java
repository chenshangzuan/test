package kled.test.service;

import api.HelloService;
import common.Result;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author: Kled
 * @version: HelloServiceImp.java, v0.1 2020-11-25 14:46 Kled
 */
@DubboService
@Path(value = "/test")
public class HelloRestServiceImp implements HelloService {

    @GET
    @Path("/rpcRest")
    @Produces({MediaType.APPLICATION_JSON})
    @Override
    public Result<String> hello(@QueryParam("msg") String msg) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setData(msg + " handler by provider");
        return result;
    }
}
