package io.github.astro.mantis.event;

import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;

import static io.github.astro.mantis.common.constant.ServiceType.EventDispatcher.DISRUPTOR;

@ServiceInterface(DISRUPTOR)
public interface EventDispatcher {

    <E extends Event<?>> void addListener(Class<E> eventType, EventListener<E> listener);

    <E extends Event<?>> void removeListener(Class<E> eventType, EventListener<E> listener);

    <E extends Event<?>> void dispatchEvent(E event);
}
