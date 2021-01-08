package kled.test.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author: Kled
 * @version: MyImportSelector.java, v0.1 2020-12-21 18:16 Kled
 */
public class MyImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        System.out.println("MyImportSelector ->" + annotationMetadata);
        return new String[]{"kled.test.bean.ImportBeanB"};
    }
}
