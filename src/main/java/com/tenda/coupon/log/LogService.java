package com.tenda.coupon.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogService {

    private final Logger logger;

    private LogService(Class<?> clazz) {
        this.logger = LoggerFactory.getLogger(clazz);
    }

    // Factory method
    public static LogService getLogger(Class<?> clazz) {
        return new LogService(clazz);
    }

    public void log(LogLevel level, String message) {
        log(level, message, "");
    }

    public void log(LogLevel level, String message, String context) {
        String formatted = LogFormatter.format(level, message, context);
        switch (level) {
            case INFO -> logger.info(formatted);
            case WARN -> logger.warn(formatted);
            case ERROR -> logger.error(formatted);
        }
    }

    public void info(String message) { log(LogLevel.INFO, message); }
    public void warn(String message) { log(LogLevel.WARN, message); }
    public void error(String message) { log(LogLevel.ERROR, message); }
}