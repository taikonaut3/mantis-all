package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.event.Event;
import io.github.astro.mantis.event.EventListener;

import java.util.concurrent.Executor;

public abstract class EnvelopeEventListener<T extends Event<?>> implements EventListener<T> {

    protected MantisApplication mantisApplication;

    protected Executor executor;

    public EnvelopeEventListener(Executor executor, MantisApplication mantisApplication) {
        this.executor = executor;
        this.mantisApplication = mantisApplication;
    }

    @Override
    public void onEvent(T event) {
        executor.execute(() -> handEnvelopeEvent(event));
    }

    protected abstract void handEnvelopeEvent(T event);

}
