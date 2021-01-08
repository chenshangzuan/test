package java_base.openFeign;

import feign.*;

import java.util.Map;

/**
 * @author: Kled
 * @version: FeignClient.java, v0.1 2020-12-28 10:39 Kled
 */
@Headers({"Accept: application/json"})
public interface FeignClient {

    @RequestLine("GET /feign/test")
    String doGet(@QueryMap Map<String, Object> params);

    @RequestLine("GET /feign/test?msg={msg}")
    String doGet2(@Param("msg") String msg, @HeaderMap Map<String, Object> headerMap);

    @RequestLine("GET /feign/test")
    String doGet3(@QueryMap(encoded = true) QueryRequest request);

    @RequestLine("POST /feign/test")
    @Headers("Content-Type: application/json")
    @Body("%7B\"msg\":\"{msg}\" %7D")
    String doPost(@Param("msg") String msg);
    //String doPost(HelloRequest request);
}
