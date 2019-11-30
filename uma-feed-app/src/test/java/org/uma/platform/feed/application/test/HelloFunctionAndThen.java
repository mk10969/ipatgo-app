package org.uma.platform.feed.application.test;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import java.util.function.Function;

public class HelloFunctionAndThen {

    Function<String, String> wrapDoubleQuotation = str -> "\"" + str + "\"";
    Function<String, String> wrapSingleQuotation = str -> "'" + str + "'";

    Function<String, String> wrapDoubleAndSingleQuotation = wrapDoubleQuotation.andThen(wrapSingleQuotation);


    @Test
    void test() {
        String result = wrapDoubleAndSingleQuotation.apply("hoge");
        System.out.println(result);
    }


}
