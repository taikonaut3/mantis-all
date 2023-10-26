package io.github.astro.mantis.event;

public interface EventListener<E extends Event<?>> {

    void onEvent(E event);

    Class<E> getEventType();

    boolean check(E event);
}
