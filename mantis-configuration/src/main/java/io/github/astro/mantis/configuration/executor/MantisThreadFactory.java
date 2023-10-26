package io.github.astro.mantis.configuration.executor;

import io.github.astro.mantis.common.util.AssertUtils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MantisThreadFactory implements ThreadFactory {
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadExceptionHandler threadExceptionHandler = new ThreadExceptionHandler();
    private final String namePrefix;
    private final boolean isDaemon;

    public MantisThreadFactory(String namePrefix, boolean isDaemon) {
        this.namePrefix = namePrefix;
        this.isDaemon = isDaemon;
    }

    public MantisThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
        this.isDaemon = false;
    }

    @Override
    public Thread newThread(Runnable r) {
        AssertUtils.assertNotNull(r);
        Thread t = new Thread(r, "Mantis-" + namePrefix + "-Thread-" + threadNumber.getAndIncrement());
        t.setUncaughtExceptionHandler(threadExceptionHandler);
        t.setDaemon(isDaemon);
        return t;
    }
}