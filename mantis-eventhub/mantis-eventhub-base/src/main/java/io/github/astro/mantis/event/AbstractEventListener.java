package io.github.astro.mantis.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;

public abstract class AbstractEventListener<E extends Event<?>> implements EventListener<E> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventListener.class);
    protected Set<EventType> eventTypes = new HashSet<>();
    private Executor executor;

    public AbstractEventListener(Executor executor) {
        this.executor = executor;
    }

    public Executor getExecutor() {
        return executor;
    }

    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void onEvent(E event) {
        if (check(event)) {
            logger.debug("Received Event(" + event.getClass().getSimpleName() + ") current Listener : " + this.getClass().getSimpleName());
            try {
                handleEvent(event);
            } catch (Exception e) {
                logger.error("Handle Failed Event(" + event.getClass().getSimpleName() + ") current Listener ", e);
                throw new RuntimeException(e);
            }
        }
    }

    protected abstract void handleEvent(E event);

    @Override
    public boolean check(E event) {
        return eventTypes.contains(event.getType()) && !event.isPropagationStopped();
    }

    public Set<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(Set<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

}
