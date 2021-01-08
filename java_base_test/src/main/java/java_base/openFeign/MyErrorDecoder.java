package java_base.openFeign;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * @author: Kled
 * @version: MyErrorDecoder.java, v0.1 2020-12-28 14:44 Kled
 */
public class MyErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        System.out.println("MyErrorDecoder -> s=" + s + " , response=" + response);
        return new Exception();
    }
}
