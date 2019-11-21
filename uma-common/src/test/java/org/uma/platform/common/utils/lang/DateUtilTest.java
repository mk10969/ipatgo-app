package org.uma.platform.common.utils.lang;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void test_日付比較() {
        LocalDateTime dateTime = LocalDateTime.of(2017, 1, 1, 1, 1, 1);

        // 今から３年より昔であるかどうか。
        // ３年より昔ではない！
        assertTrue(LocalDateTime.now().minusYears(3L).isBefore(dateTime));

    }


}