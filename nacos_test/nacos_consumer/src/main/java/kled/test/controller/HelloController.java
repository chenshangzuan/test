package kled.test.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.base.Joiner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * @author: Kled
 * @version: HelloController.java, v0.1 2020-11-25 15:05 Kled
 */
@RestController
@RequestMapping(value = "/test")
public class HelloController {

    @NacosInjected
    private NamingService namingService;

    @NacosValue(value = "${consumer_name:11}", autoRefreshed = true)
    private String name;

    @RequestMapping(value = "hello")
    public String hello(){
        return name;
    }

    @RequestMapping(value = "/nacos")
    public String listServices() throws NacosException {
        return Joiner.on(",").join(namingService.getAllInstances("nacos_provider").stream().map(Instance::toString).collect(Collectors.toList()));
    }
}
