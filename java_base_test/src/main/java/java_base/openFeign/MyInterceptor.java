package java_base.openFeign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author: Kled
 * @version: MyInterceptor.java, v0.1 2020-12-28 14:27 Kled
 */
public class MyInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println("MyInterceptor -> " + requestTemplate.url());
    }
}
