package kled.test.spi.impl;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Mysql;
import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * @author: Kled
 * @version: SecondWrapperSPI.java, v0.1 2020-12-20 22:38 Kled
 */
@Activate(order = 2)
public class SecondWrapperSPI implements MySPI {

    private MySPI mySPI;

    public SecondWrapperSPI(MySPI mySPI) {
        this.mySPI = mySPI;
    }

    @Override
    public String helloSpi(URL url) {
        System.out.println("I'm second wrapper");
        return mySPI.helloSpi(url);
    }
}
