package org.uma.platform.common.utils.lang;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateUtilTest {


    @Test
    void test() {
        LocalDateTime dateTime = LocalDateTime.now();
        String localDatetime = DateUtil.format("yyyyMMddHHmmss", dateTime);


        String zonedDateTime = DateTimeFormatter
                .ofPattern("yyyyMMddHHmmss")
                .format(ZonedDateTime.now());

        assertEquals(localDatetime, zonedDateTime);

    }

    @Test
    void testtest(){
        DateUtil.tolocalDateTime(null);



    }
}