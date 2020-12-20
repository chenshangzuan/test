package kled.test.service;

import api.HelloService;
import common.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author: Kled
 * @version: HelloServiceImp.java, v0.1 2020-11-25 14:46 Kled
 */
@DubboService
@Path(value = "/test")
@Api(tags = "Hello Restful RPC接口")
public class HelloRestServiceImp implements HelloService {

    @GET
    @Path("/rpcRest")
    @Produces({MediaType.APPLICATION_JSON})
    @ApiOperation(value = "Hello方法")
    @Override
    public Result<String> hello(@QueryParam("msg") @ApiParam(value = "消息MSG") String msg) {
        Result<String> result = new Result<>();
        result.setSuccess(true);
        result.setData(msg + " handler by provider, attachment value=" + RpcContext.getContext().getAttachment("attachmentKey"));
        return result;
    }
}
