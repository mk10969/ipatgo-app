package org.uma.platform.feed.application.jvlink.test;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class LambdaTest3 {

    public static class Camera {
        private Function<Color, Color> filter;

        public Color capture(final Color color) {
            final Color processedColor = filter.apply(color);
            System.out.println(processedColor);
            return processedColor;
        }

        // このメソッドだと、一つのフィルターしか設定できない。
        public void setFilter(final Function<Color, Color> filter) {
            this.filter = filter;
        }

        @SafeVarargs
        public final void setFilters(final Function<Color, Color>... filters) {
            this.filter = Stream.of(filters)
                    .reduce((filter, next) -> filter.compose(next))
                    .orElseGet(Function::identity);
            //.orElse(color -> color)
            // 引数をそのまま返すなら、Function::identityがある。
        }
    }

    @Test
    void test() {
        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo) ->
                camera.capture(new Color(200, 200, 200));
        camera.setFilter(Color::brighter);
        printCaptured.accept("brighter !!");
    }

    @Test
    void test2() {
        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo) ->
                camera.capture(new Color(200, 200, 200));
        camera.setFilter(Color::darker);
        printCaptured.accept("darker !!");
    }

    @Test
    void test3() {
        final Camera camera = new Camera();
        final Consumer<String> printCaptured = (filterInfo) ->
                camera.capture(new Color(200, 200, 200));
        camera.setFilters(Color::darker, Color::brighter);
        printCaptured.accept("darker and brighter!!");
    }


}
