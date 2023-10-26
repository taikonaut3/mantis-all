package io.github.astro.mantis.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractEventDispatcher implements EventDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEventDispatcher.class);
    protected final Map<Class<? extends Event<?>>, List<EventListener<?>>> listenerMap;

    protected AbstractEventDispatcher() {
        listenerMap = new ConcurrentHashMap<>();
    }

    @Override
    public <E extends Event<?>> void addListener(Class<E> eventType, EventListener<E> listener) {
        if (listenerMap.containsKey(eventType)) {
            listenerMap.get(eventType).add(listener);
        } else {
            List<EventListener<?>> listeners = new ArrayList<>();
            listeners.add(listener);
            listenerMap.put(eventType, listeners);
        }
        logger.debug("Register Listener for Event(" + eventType.getSimpleName() + ") -> " + listener.getClass().getSimpleName());
    }

    @Override
    public <E extends Event<?>> void removeListener(Class<E> eventType, EventListener<E> listener) {
        if (listenerMap.containsKey(eventType)) {
            List<EventListener<?>> listeners = listenerMap.get(eventType);
            listeners.remove(listener);
            if (listeners.isEmpty()) {
                listenerMap.remove(eventType);
            }
        }
        logger.debug("Remove Listener for (" + eventType.getSimpleName() + ") -> " + listener.getClass().getSimpleName());
    }

    @Override
    public <E extends Event<?>> void dispatchEvent(E event) {
        if (!event.isPropagationStopped()) {
            doDispatchEvent(event);
        }
    }

    protected abstract <E extends Event<?>> void doDispatchEvent(E event);
}
