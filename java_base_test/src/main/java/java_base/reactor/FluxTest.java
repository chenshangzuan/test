package java_base.reactor;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

import java.util.concurrent.TimeUnit;

/**
 * @author: Kled
 * @version: FluxTest.java, v0.1 2021-02-07 10:52 Kled
 */
public class FluxTest {

    public static void main(String[] args) {
        Flux.just("a b c d")
                .flatMap(x -> Flux.fromArray(x.split("\\s+")))
                .filter(x -> !"a".equals(x))
                .take(2)
                .doFinally(type -> {
                    if (type == SignalType.ON_COMPLETE){
                        System.out.println("do something at finally");
                    }
                })
                .subscribe(System.out::println);

        //合并
        Flux<Integer> flux1 = Flux.just(1, 2);
        Flux<Integer> flux2 = Flux.just(3, 4);
        Flux.zip(flux1, flux2).map(tuple2 -> tuple2.getT1() + tuple2.getT2()).subscribe(System.out::println);

        StepVerifier.create(Flux.range(1, 6)
                .filter(i -> i % 2 == 1)    // 1
                .map(i -> i * i))
                .expectNext(1, 9, 25)   // 2
                .verifyComplete();
    }

    //背压测试
    //背压实现的关键类：Subscription(Publisher和Subscriber的中间人<缓冲>)
    public void testBackPressure(){
        Flux.range(1, 6)    // 1
                .doOnRequest(n -> System.out.println("Request " + n + " values..."))    // 2
                .subscribe(new BaseSubscriber<Integer>() {  // 3
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) { // 4
                        System.out.println("Subscribed and make a request...");
                        request(1); // 5
                    }

                    @Override
                    protected void hookOnNext(Integer value) {  // 6
                        try {
                            TimeUnit.SECONDS.sleep(1);  // 7
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Get value [" + value + "]");    // 8
                        request(1); // 9
                    }
                });
    }
}
