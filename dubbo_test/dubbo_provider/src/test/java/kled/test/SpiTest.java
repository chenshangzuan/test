package kled.test;

import kled.test.constant.CommonConstant;
import kled.test.spi.MySPI;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.AdaptiveClassCodeGenerator;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

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

    @Test
    public void testActiveExtension(){
        //场景1：如果指定的key对应url中的paramValue包含对应实现类的名称，则按名称过滤规则(**命中名称的实现类忽略@Activate中的value和group条件**)。例如：url?param=a,-b标识激活名了"a"的实现类，过滤"b"的实现类<br>
        //场景2: 如果未被指定value[]包含的实现类，则按@Activate中的激活条件判断，其中group和value未指定均不做判断(默认支持激活)
        // - group: 分组
        // - value: 标签值
        //   - 指定了value, 则url中必须有匹配的参数。例如:value="aa:bb", 则url?aa=bb才激活；value="aa", 则url?aa=xxx任意值即可激活
        // - order: 排序

        //测试1: 指定leon, 排除sam, 且不设置参数aa=bb(kled的条件)，不设置group条件
        String urlStr1 = "http://localhost:10010/test/hello?actionCondition=leon,-sam";
        System.out.println(urlStr1);
        URL url1 = URL.valueOf(urlStr1);

        List<MySPI> mySPIs1 = ExtensionLoader.getExtensionLoader(MySPI.class).getActivateExtension(url1, "actionCondition");
        //只返回leon, kled不满足@Activate的value条件，sam被过滤
        mySPIs1.forEach(x -> System.out.println(x.helloSpi(url1)));
        System.out.println();

        String urlStr2 = "http://localhost:10010/test/hello?actionCondition=leon&aa=bb";
        System.out.println(urlStr2);
        URL url2 = URL.valueOf(urlStr2);
        List<MySPI> mySPIs2 = ExtensionLoader.getExtensionLoader(MySPI.class).getActivateExtension(url2, "actionCondition");
        //leon, kled和sam, 其中后两个都满足@Activate条件
        mySPIs2.forEach(x -> System.out.println(x.helloSpi(url2)));
        System.out.println();

        String urlStr3 = "http://localhost:10010/test/hello?actionCondition=leon,-kled,-sam";
        System.out.println(urlStr3);
        URL url3 = URL.valueOf(urlStr3);
        List<MySPI> mySPIs3 = ExtensionLoader.getExtensionLoader(MySPI.class).getActivateExtension(url3, "actionCondition", CommonConstant.DEV_ROLE);
        //只返回leon，只要按名字匹配的上，忽略@Activate的限制条件
        mySPIs3.forEach(x -> System.out.println(x.helloSpi(url3)));
        System.out.println();

        //直接设置实现类名字列表 String[] values
        String urlStr4 = "http://localhost:10010/test/hello";
        System.out.println(urlStr4);
        URL url4 = URL.valueOf(urlStr4);
        String[] values = new String[]{"leon", "-kled", "-sam"};
        List<MySPI> mySPIs4 = ExtensionLoader.getExtensionLoader(MySPI.class).getActivateExtension(url4, values);
        mySPIs4.forEach(x -> System.out.println(x.helloSpi(url4)));
    }
}
