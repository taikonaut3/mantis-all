package io.github.astro.mantis.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateUtils {

    String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    String COMPACT_FORMAT = "yyyyMMddHHmmss";

    static String format(LocalDateTime dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    static LocalDateTime parse(String str, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(str, formatter);
    }

    static String format(LocalDateTime dateTime) {
        return format(dateTime, DATETIME_FORMAT);
    }

}