package org.uma.external.ipatgo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class IPatGoClientExeTest {

    @Autowired
    private IPatGoProperties iPatGoProperties;

    @Test
    void test() {
        List<String> actual = new IPatGoExe.CommandBuilder(iPatGoProperties)
                .setMode(IPatGoExe.Mode.data)
                .setArgument("aaa,aaaa,aaaa,aaaaaa")
                .setNoSplash()
                .build();
        actual.forEach(System.out::println);
    }

    @Test
    void test2() {
        List<String> actual = new IPatGoExe.CommandBuilder(iPatGoProperties)
                .setMode(IPatGoExe.Mode.history)
                .setTimeSeries(IPatGoExe.TimeSeries.before)
                .build();
        actual.forEach(System.out::println);
    }


}