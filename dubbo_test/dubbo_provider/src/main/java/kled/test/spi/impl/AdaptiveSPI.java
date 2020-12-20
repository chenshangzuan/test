package kled.test.spi.impl;

import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

/**
 * @author: Kled
 * @version: SamSPI.java, v0.1 2020-12-20 22:26 Kled
 */
//@Adaptive
//如果注解在类上，自适应拓展的唯一实现(不常用)
public class AdaptiveSPI implements MySPI {

    @Override
    public String helloSpi(URL url) {
        return "I'm Admin";
    }
}
