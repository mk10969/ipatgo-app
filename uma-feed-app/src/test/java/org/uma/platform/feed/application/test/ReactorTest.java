package org.uma.platform.feed.application.test;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

import java.time.Duration;

public class ReactorTest {

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

    @Test
    void pass3() {
        Flux.just("aaa,bb", "b,ccc,ddddd,e", "ee,ff", "ff,g,hh,i", "i", "iii,jjj,")
                .repeat()
//                .flatMap(i -> Flux.from())
//
//                .reduce((i, j) -> i + j)
                .doOnNext(System.out::println)
//                .flatMapMany(i -> Flux.fromArray(i.split(",")))
//                .doOnNext(System.out::println)
//                .take(3)
                .subscribe();
    }


    @Test
    void testtest() {
        String str = "aaa,bbb,ccc,ddddd,eee,ffff,g,hh,iiiii,jjj,";
        String[] arr = str.split(",");
        // 最後のカンマ以降のデータはなしと見られるのね。
        System.out.println(arr.length);
    }


}
