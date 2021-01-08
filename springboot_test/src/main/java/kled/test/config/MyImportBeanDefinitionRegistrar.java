package kled.test.config;

import kled.test.bean.ImportBeanC;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: Kled
 * @version: MyImportBeanDefinitionRegistrar.java, v0.1 2020-12-21 18:25 Kled
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(ImportBeanC.class);
        builder.addPropertyValue("msg", "张三");
        registry.registerBeanDefinition("importBeanC", builder.getBeanDefinition());
    }
}
