package org.uma.platform.common.utils.lang;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class DateUtil {

    private DateUtil() {
    }

    public static String format(String format, LocalDateTime dateTime){
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);
    }

    public static LocalDateTime tolocalDateTime(long epochSecond){
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }

    public static LocalDateTime tolocalDateTimeWithLimit(long epochSecond){
        if (epochSecond < 10000000){
            throw new IllegalArgumentException(epochSecond + "の値は、");
        }
        return tolocalDateTime(epochSecond);
    }

}
