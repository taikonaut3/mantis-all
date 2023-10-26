package io.github.astro.mantis.event;

import io.github.astro.mantis.configuration.extension.Accessor;

public interface Event<S> extends Accessor<Object> {

    EventType getType();

    S getSource();

    void setSource(S source);

    /**
     * 该方法将事件冒泡停止。当事件处理程序调用这种方法时，事件将停止冒泡，不会再次触发。
     */
    void stopPropagation();

    boolean isPropagationStopped();

}
