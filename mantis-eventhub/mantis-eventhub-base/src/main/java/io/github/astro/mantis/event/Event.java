package io.github.astro.mantis.event;

import io.github.astro.mantis.configuration.extension.Accessor;

/**
 * Event Object.
 *
 * @param <S> the type of the event source
 */
public interface Event<S> extends Accessor<Object> {

    /**
     * Returns the source of the event.
     *
     * @return the event source
     */
    S getSource();

    /**
     * Sets the source of the event.
     *
     * @param source the event source to set
     */
    void setSource(S source);

    /**
     * Stops the propagation of the event. Once the propagation is stopped,
     * subsequent listeners will not receive the event.
     */
    void stopPropagation();

    /**
     * Checks if the propagation of the event is stopped.
     *
     * @return true if the propagation is stopped, false otherwise
     */
    boolean isPropagationStopped();

}
