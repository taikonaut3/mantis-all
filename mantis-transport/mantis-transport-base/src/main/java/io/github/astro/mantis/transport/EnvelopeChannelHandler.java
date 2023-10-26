package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.channel.AbstractChannelHandler;
import io.github.astro.mantis.transport.channel.Channel;

public abstract class EnvelopeChannelHandler extends AbstractChannelHandler {

    protected final EventDispatcher eventDispatcher;

    protected final MantisBootStrap mantisBootStrap;

    public EnvelopeChannelHandler(MantisBootStrap mantisBootStrap) {
        this.mantisBootStrap = mantisBootStrap;
        eventDispatcher = ServiceProviderLoader.loadService(EventDispatcher.class);
    }

    @Override
    public void received(Channel channel, Object message) throws NetWorkException {
        if (message instanceof Envelope envelope) {
            convert(envelope);
            receivedEnvelope(channel, envelope);
        }
    }

    protected abstract void convert(Envelope envelope);

    protected abstract void receivedEnvelope(Channel channel, Envelope envelope);
}
