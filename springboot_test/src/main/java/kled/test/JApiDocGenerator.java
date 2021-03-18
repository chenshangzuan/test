package kled.test;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @author: Kled
 * @version: JApiDocGenerator.java, v0.1 2021-02-22 11:10 Kled
 */
public class JApiDocGenerator {

    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        // 项目根目录(文件夹目录)
        config.setProjectPath("/Users/kled/git/test/springboot_test");
        // 项目名称
        config.setProjectName("ApiDocsDemo");
        // 声明该API的版本
        config.setApiVersion("V1.0");
        // 生成API 文档所在目录
        config.setDocsPath("/Users/kled/git/test/springboot_test/src/main/resources/apidocs");
        // 配置自动生成
        config.setAutoGenerate(Boolean.TRUE);
        config.setMvcFramework("spring");
        config.addJavaSrcPath("/Users/kled/git/test/springboot_test/src/main/java");
        // 执行生成文档
        Docs.buildHtmlDocs(config);
    }
}
