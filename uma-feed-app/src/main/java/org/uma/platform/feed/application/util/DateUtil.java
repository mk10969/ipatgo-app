package org.uma.platform.feed.application.util;

import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    public static String format(String format, LocalDateTime dateTime) {
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);
    }

    public static LocalDateTime tolocalDateTime(long epochSecond) {
        return LocalDateTime.ofEpochSecond(epochSecond, 0, ZoneOffset.UTC);
    }

    public static LocalDateTime within3years(LocalDateTime dateTime) {
        // 現在時刻より、３年以内である。
        if (LocalDateTime.now().minusYears(3L).isBefore(dateTime)) {
            // 3年以内だから、引数をそのまま返す。
            return dateTime;
        }
        throw new IllegalArgumentException("現在時刻から、３年以内のデータしか取得できません。");
    }

    public static Mono<LocalDateTime> lastWeek() {
        return Mono.just(LocalDateTime.now()
                .minusWeeks(1L));
    }

}
