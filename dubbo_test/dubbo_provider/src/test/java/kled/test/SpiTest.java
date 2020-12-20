package kled.test;

import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.AdaptiveClassCodeGenerator;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

/**
 * @author: Kled
 * @version: SpiTest.java, v0.1 2020-12-20 22:28 Kled
 */
public class SpiTest extends SpringTestDubboProviderSpringBootApplicationTests {

    @Test
    public void testExtension(){
        ExtensionLoader<MySPI> loader = ExtensionLoader.getExtensionLoader(MySPI.class);
        //name必须存在对于的实现类
        //MySPI mySPI = loader.getExtension("admin");
        MySPI mySPI = loader.getExtension("kled");
        System.out.println(mySPI.helloSpi(URL.valueOf("http://localhost:10010/test/hello")));
    }

    @Test
    public void testAdaptiveExtension(){
        String code = (new AdaptiveClassCodeGenerator(MySPI.class, "kled")).generate();
        System.out.println(code);
        System.out.println();

        MySPI mySPI = ExtensionLoader.getExtensionLoader(MySPI.class).getAdaptiveExtension();
        System.out.println(mySPI.helloSpi(URL.valueOf("http://localhost:10010/test/hello?my.s.p.i=leon")));
        System.out.println();

        System.out.println(mySPI.helloSpi(URL.valueOf("http://localhost:10010/test/hello?samSignature=sam")));
    }


}
