package kled.test.spi.impl;

import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;

/**
 * @author: Kled
 * @version: SamSPI.java, v0.1 2020-12-20 22:26 Kled
 */
public class SamSPI implements MySPI {

    @Override
    public String helloSpi(URL url) {
        return "I'm Sam";
    }
}
