package kled.test.config;

import kled.test.bean.ImportBeanA;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author: Kled
 * @version: ImportConfig.java, v0.1 2020-12-21 18:08 Kled
 */
@Configuration
@Import({ImportBeanA.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class ImportConfig {
}
