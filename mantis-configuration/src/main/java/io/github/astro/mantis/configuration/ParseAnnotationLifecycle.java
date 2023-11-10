package io.github.astro.mantis.configuration;

public abstract class ParseAnnotationLifecycle implements Lifecycle {

    @Override
    public void init() {
        initBefore();
        doInit();
        initAfter();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    protected abstract void initBefore();

    protected abstract void doInit();

    protected abstract void initAfter();

}
