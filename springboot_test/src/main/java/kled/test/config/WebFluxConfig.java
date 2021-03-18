package kled.test.config;

import kled.test.bean.WebFluxHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.*;


/**
 * @author: Kled
 * @version: WebFluxConfig.java, v0.1 2021-02-09 11:33 Kled
 */
@Configuration
public class WebFluxConfig {

    //注：调用失败GET /webFluxRoute1
    @Bean
    public RouterFunction<ServerResponse> router(WebFluxHandler webFluxHandler) {
        return RouterFunctions.route().GET("/webFluxRoute1", webFluxHandler::hello).build();
//        return RouterFunctions.route(RequestPredicates.GET("/webFluxRoute1").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), webFluxHandler::hello)
//                .andRoute(RequestPredicates.GET("/webFluxRoute2"), webFluxHandler::hello);
    }

//    private Mono<ServerResponse> hello(ServerRequest request) {
//        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
//                .body(BodyInserters.fromValue("Hello, Spring Flux! from " + request.uri().getPath()));
//    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();
    }
}
