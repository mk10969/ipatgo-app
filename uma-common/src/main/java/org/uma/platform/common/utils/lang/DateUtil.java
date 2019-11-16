package org.uma.platform.common.utils.lang;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    private DateUtil() {
    }

    public static String format(String format, ZonedDateTime dateTime){
        return DateTimeFormatter
                .ofPattern(format)
                .format(dateTime);

    }
}
