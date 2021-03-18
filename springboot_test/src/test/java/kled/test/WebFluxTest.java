package kled.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author: Kled
 * @version: WebFluxTest.java, v0.1 2021-02-09 14:21 Kled
 */
public class WebFluxTest extends SpringTestISpringBootApplicationTests {
    @Autowired
    private WebClient webClient;

    @Test
    public void testWebClient() throws InterruptedException {
        Mono<String> mono = webClient.get().uri("http://localhost:10000/webFlux").retrieve().bodyToMono(String.class);
        mono.subscribe(System.out::println);
        Thread.sleep(3000);
    }
}
