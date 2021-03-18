package kled.test.controller.web_flux;

import com.google.common.base.Stopwatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * @author: Kled
 * @version: HelloWebFluxController.java, v0.1 2021-02-09 11:24 Kled
 */
@RestController
public class HelloWebFluxController {

    @GetMapping("/webFlux")
    public Mono<String> getUser() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Mono<String> mono = Mono.fromSupplier(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello, WebFlux !";
        });
        System.out.println("webFlux request handle time:" + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return mono;
    }
}
