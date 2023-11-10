package io.github.astro.mantis.event.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.configuration.executor.MantisThreadFactory;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.event.AbstractEventDispatcher;
import io.github.astro.mantis.event.Event;
import io.github.astro.mantis.event.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static io.github.astro.mantis.common.constant.KeyValues.EventDispatcher.DISRUPTOR;

@ServiceProvider(DISRUPTOR)
public class DisruptorEventDispatcher extends AbstractEventDispatcher {

    private static final Logger logger = LoggerFactory.getLogger(DisruptorEventDispatcher.class);

    private final int bufferSize;

    private RingBuffer<EventHolder<?>> ringBuffer;

    public DisruptorEventDispatcher() {
        this(Constant.DEFAULT_BUFFER_SIZE);

    }

    public DisruptorEventDispatcher(int bufferSize) {
        this.bufferSize = bufferSize;
        createDispatcher();
    }

    private void createDispatcher() {
        Disruptor<EventHolder<?>> disruptor = new Disruptor<>(EventHolder::new, bufferSize,
                new MantisThreadFactory("Disruptor"));
        this.ringBuffer = disruptor.getRingBuffer();
        disruptor.handleEventsWith(this::handleEvent);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, EventHolder<?> event) {
                logger.error(this.getClass().getSimpleName() + " Handle Event Error", ex);
            }

            @Override
            public void handleOnStartException(Throwable ex) {
                logger.error(this.getClass().getSimpleName() + " Start Error", ex);
            }

            @Override
            public void handleOnShutdownException(Throwable ex) {
                logger.error(this.getClass().getSimpleName() + " Shutdown Error", ex);
            }
        });
        disruptor.start();
    }

    @Override
    public <E extends Event<?>> void addListener(Class<E> eventType, EventListener<E> listener) {
        super.addListener(eventType, listener);
        logger.debug("Register Listener for Event[{}],Listener: {}", eventType.getSimpleName(), listener.getClass().getSimpleName());
    }

    @Override
    public <E extends Event<?>> void removeListener(Class<E> eventType, EventListener<E> listener) {
        super.removeListener(eventType, listener);
        logger.debug("Remove Listener for Event[{}],Listener: {}", eventType.getSimpleName(), listener.getClass().getSimpleName());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <E extends Event<?>> void doDispatchEvent(E event) {
        ringBuffer.publishEvent((eventHolder, sequence) -> {
            EventHolder<E> holder = (EventHolder<E>) eventHolder;
            holder.setEvent(event);
        });
        logger.debug("DispatchEvent ({})", event.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private <E extends Event<?>> void handleEvent(EventHolder<E> holder, long sequence, boolean endOfBatch) {
        E event = holder.getEvent();
        List<EventListener<?>> listeners = listenerMap.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(event.getClass()))
                .flatMap(entry -> entry.getValue().stream())
                .toList();
        for (EventListener<?> item : listeners) {
            EventListener<E> listener = (EventListener<E>) item;
            if (listener.check(event)) {
                try {
                    listener.onEvent(event);
                } catch (Exception e) {
                    logger.error("Handle Failed Event(" + event.getClass().getSimpleName() + ") current Listener ", e);
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static class EventHolder<E extends Event<?>> {

        private E event;

        public E getEvent() {
            return event;
        }

        public void setEvent(E event) {
            this.event = event;
        }

    }

}

