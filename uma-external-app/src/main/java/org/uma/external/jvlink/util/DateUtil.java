package org.uma.external.jvlink.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

public class DateUtil {

    private static final String timeZone = "Asia/Tokyo";

    private static final Supplier<Long> now = System::currentTimeMillis;


    private DateUtil() {
    }

    public static String format(String format, LocalDateTime dateTime) {
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);
    }

//    public static LocalDateTime of(String yyyyMMdd) {
//        final DateTimeFormatter df = DateTimeFormatter.ofPattern("uuuu/MM/dd")
//                .withResolverStyle(ResolverStyle.STRICT);
//        // localDateをlocalDateTimeに変換する。
//        return LocalDateTime.of(LocalDate.parse(yyyyMMdd, df), LocalTime.of(0, 0));
//    }

    public static LocalDateTime toLocalDateTime(long epochSecond) {
        if (String.valueOf(epochSecond).length() != 13) {
            throw new IllegalArgumentException("milliseconds にしてください。");
        }
        return Instant.ofEpochMilli(epochSecond)
                .atZone(ZoneId.of(timeZone))
                .toLocalDateTime();
    }

    @Deprecated
    public static LocalDateTime lastWeek() {
        return toLocalDateTime(now.get()).minusWeeks(1L);
    }

}
