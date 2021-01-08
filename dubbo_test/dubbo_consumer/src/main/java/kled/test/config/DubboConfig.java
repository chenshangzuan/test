package kled.test.config;

import api.HelloService;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Kled
 * @version: DubboConfig.java, v0.1 2020-12-20 21:26 Kled
 */
@Configuration
public class DubboConfig {

    @Value("${dubbo.registry.address}")
    private String registryAddress;

    @Bean
    public ReferenceBean<HelloService> myReferenceBean(){
        ReferenceBean<HelloService> referenceBean = new ReferenceBean<HelloService>();
        referenceBean.setInterface(HelloService.class);
        referenceBean.setVersion("1.0.2");
        RegistryConfig registryConfig = new RegistryConfig();

        registryConfig.setAddress(registryAddress);
        referenceBean.setRegistry(registryConfig);

        //是否初始化
        referenceBean.setInit(true);
        return referenceBean;
    }
}
