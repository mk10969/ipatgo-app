package org.uma.extarnal.ipatgo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class IPatGoConfigurationTest {

    @Autowired
    private IPatGoProperties iPatGoProperties;

    @Test
    void test() {
        List<String> actual = new IPatGoConfiguration.Builder(iPatGoProperties)
                .setMode(IPatGoConfiguration.Mode.data)
                .setArgument("aaa,aaaa,aaaa,aaaaaa")
                .setNoSplash()
                .build();
        actual.forEach(System.out::println);
    }

    @Test
    void test2() {
        List<String> actual = new IPatGoConfiguration.Builder(iPatGoProperties)
                .setMode(IPatGoConfiguration.Mode.history)
                .setTimeSeries(IPatGoConfiguration.TimeSeries.before)
                .build();
        actual.forEach(System.out::println);
    }


}