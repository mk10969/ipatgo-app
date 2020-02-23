package org.uma.external.jvlink.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class DateUtil {

    private DateUtil() {
    }

    public static String format(String format, LocalDateTime dateTime) {
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);
    }

    public static LocalDateTime of(String yyyyMMdd) {
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("uuuu/MM/dd")
                .withResolverStyle(ResolverStyle.STRICT);
        // localDateをlocalDateTimeに変換する。
        return LocalDateTime.of(LocalDate.parse(yyyyMMdd, df), LocalTime.of(0, 0));
    }

    public static LocalDateTime toLocalDateTime(long epochSecond) {
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }
    
}
