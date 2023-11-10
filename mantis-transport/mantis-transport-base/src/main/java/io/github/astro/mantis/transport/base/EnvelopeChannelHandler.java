package io.github.astro.mantis.transport.base;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.common.exception.SourceException;
import io.github.astro.mantis.configuration.Caller;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.channel.Channel;

public abstract class EnvelopeChannelHandler extends DefaultChannelHandlerAdapter {

    protected final MantisApplication mantisApplication;

    public EnvelopeChannelHandler(MantisApplication mantisApplication) {
        this.mantisApplication = mantisApplication;
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Envelope envelope) {
            Caller caller = getCurrentCaller(envelope);
            convert(envelope, caller);
            receivedEnvelope(channel, envelope, caller);
        }
    }

    protected abstract Caller getCurrentCaller(Envelope envelope) throws SourceException;

    protected abstract void convert(Envelope envelope, Caller caller);

    protected abstract void receivedEnvelope(Channel channel, Envelope envelope, Caller caller);

}
