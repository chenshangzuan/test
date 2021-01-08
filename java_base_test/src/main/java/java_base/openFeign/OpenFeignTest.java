package java_base.openFeign;

import com.google.common.collect.Maps;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.okhttp.OkHttpClient;

import java.util.Map;

/**
 * @author: Kled
 * @version: OpenFeignTest.java, v0.1 2020-12-28 10:39 Kled
 */
public class OpenFeignTest {
    public static void main(String[] args) {
        FeignClient feignClient = Feign.builder()
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .queryMapEncoder(new MyJacksonQueryMapEncoder())
                .logger(new Logger.JavaLogger("FeignClient.Logger")
                .appendToFile("http.log"))
                .logLevel(Logger.Level.FULL)
                .requestInterceptor(new MyInterceptor())
                .errorDecoder(new MyErrorDecoder())
                //.retryer(new MyRetryer)
                .client(new OkHttpClient()).target(FeignClient.class, "http://127.0.0.1:10000");

        Map<String, Object> params = Maps.newHashMap();
        params.put("msg", "[GET] hello open feign");
        System.out.println(feignClient.doGet(params));

        Map<String, Object> headerMap = Maps.newHashMap();
        params.put("key", "value");
        System.out.println(feignClient.doGet2("[GET2] hello open feign", headerMap));

        QueryRequest queryRequest = new QueryRequest();
        //[GET3]报错
        //https://zhuanlan.zhihu.com/p/49263306Invalid character found in the request target. The valid characters are defined in RFC 7230 and RFC 3986
        queryRequest.setMsg("GET3: hello open feign");
        System.out.println(feignClient.doGet3(queryRequest));

        System.out.println(feignClient.doPost("[POST] hello open feign"));
    }
}
