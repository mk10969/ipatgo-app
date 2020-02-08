package org.uma.extarnal.ipatgo;

import org.junit.jupiter.api.Test;

import java.util.List;

class IPatGoConfigurationTest {

    private IPatGoProperties ipatGoProperties = new IPatGoProperties(
            "111", "aaaa", "aaa", "111");

    @Test
    void test() {
        List<String> actual = new IPatGoConfiguration.Builder(ipatGoProperties)
                .setMode(IPatGoConfiguration.Mode.data)
                .setArgument("aaa,aaaa,aaaa,aaaaaa")
                .setNoSplash()
                .build();
        actual.forEach(System.out::println);
    }

    @Test
    void test2() {
        List<String> actual = new IPatGoConfiguration.Builder(ipatGoProperties)
                .setMode(IPatGoConfiguration.Mode.history)
                .setTimeSeries(IPatGoConfiguration.TimeSeries.before)
                .build();
        actual.forEach(System.out::println);
    }


}