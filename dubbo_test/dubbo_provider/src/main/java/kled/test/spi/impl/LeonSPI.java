package kled.test.spi.impl;

import kled.test.constant.CommonConstant;
import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * @author: Kled
 * @version: LeonSPI.java, v0.1 2020-12-20 22:25 Kled
 */
@Activate(group = CommonConstant.MNG_ROLE)
public class LeonSPI implements MySPI {
    @Override
    public String helloSpi(URL url) {
        return "I'm Leon";
    }
}
