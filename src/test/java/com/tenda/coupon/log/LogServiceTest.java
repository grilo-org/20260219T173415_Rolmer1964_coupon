package com.tenda.coupon.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.*;
import org.slf4j.LoggerFactory;

class LogServiceTest {

    private LogService logService;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    void setUp() {
        logService = LogService.getLogger(LogServiceTest.class);

        Logger logger = (Logger) LoggerFactory.getLogger(LogServiceTest.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    void tearDown() {
        listAppender.stop();
    }

    @Test
    void testInfoLogsMessage() {
        logService.info("test message");
        boolean found = listAppender.list.stream()
                .anyMatch(event -> event.getLevel() == Level.INFO && event.getFormattedMessage().contains("test message"));
        Assertions.assertTrue(found, "Should log info message");
    }

    @Test
    void testWarnLogsMessage() {
        logService.warn("test message");
        boolean found = listAppender.list.stream()
                .anyMatch(event -> event.getLevel() == Level.WARN && event.getFormattedMessage().contains("test message"));
        Assertions.assertTrue(found, "Should log info message");
    }

    @Test
    void testErrorLogsMessage() {
        logService.error("test message");
        boolean found = listAppender.list.stream()
                .anyMatch(event -> event.getLevel() == Level.ERROR && event.getFormattedMessage().contains("test message"));
        Assertions.assertTrue(found, "Should log info message");
    }


}