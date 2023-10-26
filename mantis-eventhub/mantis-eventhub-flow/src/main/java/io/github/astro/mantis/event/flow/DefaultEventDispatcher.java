package io.github.astro.mantis.event.flow;

import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.event.AbstractEventDispatcher;
import io.github.astro.mantis.event.Event;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Flow;

import static io.github.astro.mantis.common.constant.ServiceType.EventDispatcher.FLOW;

@ServiceProvider(FLOW)
public class DefaultEventDispatcher extends AbstractEventDispatcher implements EventDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(DefaultEventDispatcher.class);

    private final Map<EventListener<?>, DefaultEventSubscriber<?>> subscriberMap = new ConcurrentHashMap<>();

    private final DefaultPublisher<Event<?>> publisher;

    public DefaultEventDispatcher() {
        publisher = new DefaultPublisher<>();
    }

    public DefaultEventDispatcher(Executor executor) {
        publisher = new DefaultPublisher<>(executor);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends Event<?>> void addListener(Class<E> eventType, EventListener<E> listener) {
        super.addListener(eventType, listener);
        DefaultEventSubscriber<?> subscriber = new DefaultEventSubscriber<>(listener);
        publisher.subscribe((Flow.Subscriber<? super Event<?>>) subscriber);
        subscriberMap.put(listener, subscriber);
    }

    @Override
    public <E extends Event<?>> void removeListener(Class<E> eventType, EventListener<E> listener) {
        super.removeListener(eventType, listener);
        DefaultEventSubscriber<?> subscriber = subscriberMap.get(listener);
        subscriber.cancel();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <E extends Event<?>> void doDispatchEvent(E event) {
        List<EventListener<?>> listeners = listenerMap.get(event.getClass());
        if (listeners != null) {
            for (EventListener<?> listener : listeners) {
                logger.debug("Publish Event(" + event + ") to " + listener);
                publisher.publish(event, (Flow.Subscriber<? super Event<?>>) subscriberMap.get(listener));
            }
        }
    }

}
