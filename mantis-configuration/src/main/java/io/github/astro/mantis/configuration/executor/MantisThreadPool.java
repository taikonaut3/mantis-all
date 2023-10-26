package io.github.astro.mantis.configuration.executor;

import io.github.astro.mantis.common.constant.Constant;

import java.util.concurrent.*;

public class MantisThreadPool extends ThreadPoolExecutor {

    public MantisThreadPool(int corePoolSize, int maximumPoolSize, String namePrefix) {
        super(corePoolSize, maximumPoolSize,
                Constant.DEFAULT_KEEPALIVE, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(Constant.DEFAULT_CAPACITY),
                new MantisThreadFactory(namePrefix, false),
                new CallerRunsPolicy());
    }

    public MantisThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MantisThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MantisThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MantisThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    public static ExecutorService getDefaultIOExecutor(String namePrefix) {
        return new MantisThreadPool(Constant.DEFAULT_IO_THREADS, Constant.DEFAULT_IO_MAX_THREADS, namePrefix);
    }

    public static ExecutorService getDefaultCPUExecutor(String namePrefix) {
        return new MantisThreadPool(Constant.DEFAULT_CPU_THREADS, Constant.DEFAULT_CPU_MAX_THREADS, namePrefix);
    }
}
