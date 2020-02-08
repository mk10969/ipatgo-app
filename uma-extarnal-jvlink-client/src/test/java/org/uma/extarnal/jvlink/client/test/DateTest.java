package org.uma.extarnal.jvlink.client.test;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateTest {

    @Test
    public void test() {
        LocalDateTime d = LocalDateTime.now();
        System.out.println(d);
    }

    @Test
    public void test2() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime date = LocalDateTime.now();
        ZonedDateTime z1 = ZonedDateTime.now();

        System.out.println(dtf.format(date));
        System.out.println(dtf.format(z1));

        String aa = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(z1);

    }


}
