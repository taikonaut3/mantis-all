package io.github.astro.mantis.event;

import io.github.astro.mantis.configuration.extension.AbstractAccessor;

public abstract class AbstractEvent<S> extends AbstractAccessor<Object> implements Event<S> {

    protected EventType type;

    protected S data;
    protected volatile boolean propagation = false;

    public AbstractEvent() {

    }

    public AbstractEvent(S data) {
        this.data = data;
    }

    public AbstractEvent(S data, EventType type) {
        this.data = data;
        this.type = type;
    }

    @Override
    public EventType getType() {
        return type;
    }

    @Override
    public S getSource() {
        return data;
    }

    @Override
    public void setSource(S source) {
        this.data = source;
    }

    @Override
    public void stopPropagation() {
        if (!propagation) {
            synchronized (this) {
                if (!propagation) {
                    this.propagation = true;
                }
            }
        }
    }

    @Override
    public boolean isPropagationStopped() {
        return propagation;
    }
}
