package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.event.AbstractEventListener;

import java.util.concurrent.Executor;

public abstract class EnvelopeEventListener<T extends EnvelopeEvent<?>> extends AbstractEventListener<T> {

    protected MantisBootStrap mantisBootStrap;

    public EnvelopeEventListener(Executor executor, MantisBootStrap mantisBootStrap) {
        super(executor);
        this.mantisBootStrap = mantisBootStrap;
    }

    @Override
    protected void handleEvent(T event) {
        handEnvelopeEvent(event);
    }

    protected abstract void handEnvelopeEvent(T event);
}
