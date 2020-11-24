package kled.test.config;

import kled.test.common.interceptors.MyInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author: Kled
 * @version: DalConfig.java, v0.1 2020-11-21 22:45 Kled
 */
@Configuration
public class DalConfig {

    @Bean
    public Interceptor myInterceptor(){
        Interceptor interceptor = new MyInterceptor();
        Properties properties = new Properties();
        properties.setProperty("myProperties", "ha");
        interceptor.setProperties(properties);
        return interceptor;
    }
}
