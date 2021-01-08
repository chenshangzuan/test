package kled.test.spi.impl;

import kled.test.constant.CommonConstant;
import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * @author: Kled
 * @version: KledSPI.java, v0.1 2020-12-20 22:24 Kled
 */
@Activate(group = CommonConstant.DEV_ROLE, value = {"aa:bb"})
public class KledSPI implements MySPI {
    @Override
    public String helloSpi(URL url) {
        return "I'm Kled";
    }
}
