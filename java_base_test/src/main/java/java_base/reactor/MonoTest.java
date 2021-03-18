package java_base.reactor;

import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author: Kled
 * @version: MonoTest.java, v0.1 2021-02-07 10:49 Kled
 */
public class MonoTest {
    public static void main(String[] args) {
        Mono<String> mono1 = Mono.fromSupplier(() -> {
            //异步产生消息
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("produce one msg");
            return "abcd";
        })
        .map(x -> x + "e").retry(1)
        .doOnRequest(n -> System.out.println("Request " + n + " values..."))
        .doOnError(e -> System.out.println("error:" + e))
        .onErrorResume(x -> Mono.just("resume"))
        .onErrorMap(original -> new Exception("biz exception", original));

        System.out.println("consumer is waiting for msg from supplier");
        //同步订阅
        mono1.subscribe(System.out::println, System.err::println, () -> System.out.println("complete"));
        System.out.println();

        //注: 消费者不订阅，发布者不会执行以下Callable的异步操作
        Mono<String> mono2 = Mono.fromCallable(() -> {
            //异步产生消息
            try {
                System.out.println("Executor thread name="+ Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("produce one msg from callable");
            return "hello";
        })
        .doOnRequest(x -> System.out.println(x + ":" +Thread.currentThread().getName())) //在elastic调度的线程执行
        .publishOn(Schedulers.single()).map(x -> x + " world:" + Thread.currentThread().getName()) //map会在single线程中执行
        .subscribeOn(Schedulers.elastic()); //fromCallable会在elastic调度的线程执行，默认是在Main线程

        mono2.subscribe(System.out::println);
        System.out.println();

        Mono<String> mono3 = Mono.fromRunnable(() -> {
            //异步产生消息
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("produce one msg from runnable");
        });
        mono3.subscribe(System.out::println);
    }
}
