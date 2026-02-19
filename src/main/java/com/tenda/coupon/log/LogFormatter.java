package com.tenda.coupon.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogFormatter {
    public static String format(LogLevel level, String message, String context) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        return String.format("[%s] [%s] [%s] %s", timestamp, level, context, message);
    }
}
