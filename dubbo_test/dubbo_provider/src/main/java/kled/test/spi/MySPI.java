package kled.test.spi;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * @author: Kled
 * @version: MySPI.java, v0.1 2020-12-20 22:19 Kled
 */
@SPI("kled")
public interface MySPI {

    //@Adaptive = @Adaptive(value = {"my.s.p.i"}) 根据接口名转化
    @Adaptive(value = {"signature"})
    String helloSpi(URL url);
}
