package org.uma.platform.jvlink.test;


import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamIteratortest {

    private String method() {
        return "aaaaaaaaaaaaa";
    }

    @Test
    public void test() {
        Supplier<String> generator = this::method;
        Stream<String> stream5 = Stream.generate(generator).limit(5);
        stream5.forEach(s -> System.out.println(s));

    }

    @Test
    public void test2() {
        // 引数のsendが、いい。
        Stream<String> stream6 = Stream.iterate("S", (s) -> s + s);
        stream6.limit(10).forEach(s -> {
            System.out.println(s);
        });
    }

    @Test
    public void test3() {
        Iterator<Integer> iterator = new Iterator<Integer>() {
            public boolean hasNext() {
                return true;
            }

            public Integer next() {
                return null;
            }
        };

        // Iterator を Stream にする
        Spliterator<Integer> spliterator = Spliterators.spliteratorUnknownSize(iterator, 0);

        System.out.println(spliterator.estimateSize());
        System.out.println(spliterator.hasCharacteristics(Spliterator.NONNULL));
        System.out.println(spliterator.hasCharacteristics(Spliterator.IMMUTABLE));
        System.out.println(spliterator.characteristics());


        Stream<Integer> stream = StreamSupport.stream(spliterator, false);
        stream.limit(100).forEach(System.out::println);

    }

}
