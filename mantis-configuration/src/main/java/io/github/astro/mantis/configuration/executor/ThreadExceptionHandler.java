package io.github.astro.mantis.configuration.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ThreadExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        logger.error(t.getName() + "-" + t.getId() + " have a Error", e);
    }

}
