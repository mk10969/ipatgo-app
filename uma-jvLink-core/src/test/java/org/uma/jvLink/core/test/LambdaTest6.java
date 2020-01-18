package org.uma.jvLink.core.test;

import org.junit.jupiter.api.Test;
import org.uma.platform.common.utils.lang.ThreadUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LambdaTest6 {

    public static boolean evaluate(int var) {
        System.out.println("evaluate: " + var);
        ThreadUtil.sleep(2000);
        return var > 100;
    }

    public static void eagerEvaluator(final boolean input1, final boolean input2) {
        System.out.println("eagerEvaluator called...");
        System.out.println("accept?: " + (input1 && input2));
    }

    // 上記を、遅延評価する
    public static void lazyEvaluation(final Supplier<Boolean> input1, final Supplier<Boolean> input2) {
        System.out.println("eagerEvaluator called...");
        System.out.println("accept?: " + (input1.get() && input2.get()));
    }

    @Test
    void test() {
        // 引数にメソッドを持つと、引数のメソッドが、
        // 先に評価（実行）されてしまう。
        eagerEvaluator(evaluate(1), evaluate(2));
    }

    @Test
    void test2() {
        // 引数を関数にして実行
        lazyEvaluation(() -> evaluate(1), () -> evaluate(2));
    }


    public static int length(final String name) {
        Objects.requireNonNull(name);
        return name.length();
    }

    public static String toUpper(final String name) {
        Objects.requireNonNull(name);
        return name.toUpperCase();
    }


    @Test
    void test4() {
        List<String> names = Arrays.asList(
                "Brad", "Kate", "Kim", "Jack", "Joe", "Mike", "Susan", "George", "Robert", "Julia", "Parker", "Benson"
        );

        names.stream()
                .filter(name -> length(name) == 3)
                .map(name -> toUpper(name))
                .findFirst()
                .ifPresent(name -> System.out.println(name));

        // 下記のように、分割しても、終端処理(findFirst())に届くまで、中間処理が動かない。
        Stream<String> nameStream = names.stream()
                .filter(name -> length(name) == 3)
                .map(name -> toUpper(name));

        final String aaaaa = nameStream.findFirst().get();
    }


    public static class Prime {
        private static boolean isPrime(final int num) {
            return num > 1
                    && IntStream.rangeClosed(2, (int) Math.sqrt(num))
                    .noneMatch(divisor -> num % divisor == 0);
        }

        private static int primeAfter(final int num) {
            if (isPrime(num + 1)) {
                return num + 1;
            } else {
                return primeAfter(num + 1);
            }
        }

        public static List<Integer> primes(final int fromNumber, final int count) {
            // 無限Stream
            return Stream.iterate(primeAfter(fromNumber - 1), number -> primeAfter(number))
                    .limit(count)
                    .collect(Collectors.toList());

        }

    }




}
