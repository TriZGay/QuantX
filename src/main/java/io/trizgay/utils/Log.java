package io.trizgay.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Log {
    private static Logger logger;
    private static final String PREFIX = "[Quantx]:";

    public static void init() {
        System.setProperty(
                "vertx.logger-delegate-factory-class-name",
                "io.vertx.core.logging.Log4j2LogDelegateFactory"
        );
        logger = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    }

    public static void info(String message) {
        logger.info(PREFIX + message);
    }

    public static void debug(String message) {
        logger.debug(PREFIX + message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(PREFIX + message, throwable);
    }

    public static void warning(String message) {
        logger.warn(PREFIX + message);
    }
}

