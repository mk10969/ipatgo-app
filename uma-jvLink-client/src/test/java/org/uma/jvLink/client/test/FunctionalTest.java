package org.uma.jvLink.client.test;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class FunctionalTest<result> {


    @Test
    public void test() {
        Function<String, String> wrapDoubleQuotation = str -> "\"" + str + "\"";
        Function<String, String> wrapSingleQuotation = str -> "'" + str + "'";

        Function<String, String> wrapDoubleAndSingleQuotation = wrapDoubleQuotation.andThen(wrapSingleQuotation);
        String result = wrapDoubleAndSingleQuotation.apply("hoge");

        System.out.println(result);
    }

    @Test
    public void test1(String t, Function<String, Integer> function) {
        System.out.println(function.apply(t));
    }

    @Test
    public void test9() {
        String once = "once";
        test1(once, String::length);

    }

}
