package kled.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author: Kled
 * @version: SwaggerConfig.java, v0.1 2020-12-15 11:02 Kled
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket createAllApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("all").apiInfo(apiInfo())
                /*.enable(!StringUtils.equals(env, "prod"))*/.select().apis(
                        RequestHandlerSelectors.basePackage("kled.test.controller")).paths(PathSelectors.any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("controller").description("api ifo").build();
    }
}
