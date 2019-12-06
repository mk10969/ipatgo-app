package org.uma.platform.feed.application.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReactorTest {

    @Test
    void test_allメソッドテスト() throws InterruptedException {
        Flux.just("hoge", "foo", "bar")
                .all(Objects::nonNull) // 中身全てチェックするということね。
                .doOnNext(System.out::println)
                .subscribe();
        Thread.sleep(1000L);
    }


    @Test
    void test_まず初めに() {
        Flux<String> flux = Flux.just("hoge", "foo", "bar");
        StepVerifier
                .create(flux)
                .expectNext("hoge", "foo", "bar")
                .verifyComplete();
    }

    private Flux<Tuple2<Long, Long>> pass() {
        return Flux.interval(Duration.ofMinutes(1))
                .elapsed();
    }

    private Flux<String> pass2() {
        return Flux.just("aaa,bb", "b,ccc,ddddd,e", "ee,ff", "ff,g,hh,i", "i", "iii,jjj,")
                .reduce((i, j) -> i + j)
                .doOnNext(System.out::println)
                .flatMapMany(i -> Flux.fromArray(i.split(",")))
                .doOnNext(System.out::println);
    }

    @Test
    void test_カンマスピリット() {
        StepVerifier
                .create(pass2())
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .verifyComplete();
    }

    private Flux<String> flux = Flux.just("aaa,bb", "b,ccc,ddddd,e", "ee,ff", "ff,g,hh,i", "i", "iii,jjj,")
            .repeat()
            .bufferUntil(i -> i.endsWith(","))
            .flatMap(i -> Flux.fromArray(i.stream().collect(Collectors.joining()).split(",")))
            .doOnNext(System.out::println);

    @Test
    void test_カンマスピリットwithリピート_and_verifyComplete() {
        StepVerifier.create(flux)
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .verifyComplete();
    }

    @Test
    void test_カンマスピリットwithリピート_and_verify() {
        StepVerifier.create(flux)
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectNext("aaa", "bbb", "ccc", "ddddd", "eee", "ffff", "g", "hh", "iiiii", "jjj")
                .expectComplete() //完了を期待するので、エラーになる。
                .verify();
    }

    @Test
    void testtest() {
        String str = "aaa,bbb,ccc,ddddd,eee,ffff,g,hh,iiiii,jjj,";
        String[] arr = str.split(",");
        // 最後のカンマ以降のデータはなしと見られるのね。
        System.out.println(arr.length);
    }

    @Test
    void test_HotFlux() {
        // これ動きが面白いね。
        DirectProcessor<String> hotSource = DirectProcessor.create();
        Flux<String> hotFlux = hotSource.map(String::toUpperCase);

        hotFlux.subscribe(d -> System.out.println("No1: " + d));
        hotSource.onNext("blue");
        hotSource.onNext("green");

        hotFlux.subscribe(d -> System.out.println("No2: " + d));
        hotSource.onNext("orange");
        hotSource.onNext("purple");

        hotSource.onComplete(); //終わりの合図。
    }

    private Function<Flux<String>, Flux<String>> fillterAndMap = f -> f.filter(i -> !i.equals("b")).map(String::toUpperCase);

    @Test
    void test_transform() {
        Flux<String> transformFlux = Flux.fromIterable(Arrays.asList("a", "b", "c", "d"))
                .doOnNext(System.out::println)
                .transform(fillterAndMap);

        transformFlux.subscribe(System.out::println);

    }

}
