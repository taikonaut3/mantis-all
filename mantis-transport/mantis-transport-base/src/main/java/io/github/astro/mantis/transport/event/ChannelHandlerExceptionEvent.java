package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.event.AbstractEvent;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;

public class ChannelHandlerExceptionEvent extends AbstractEvent<Throwable> {

    private final Channel channel;

    private final Envelope envelope;

    private final ChannelHandler channelHandler;

    public ChannelHandlerExceptionEvent(ChannelHandler channelHandler, Channel channel, Envelope envelope, Throwable cause) {
        super(cause);
        this.channelHandler = channelHandler;
        this.channel = channel;
        this.envelope = envelope;
    }

    public Channel getChannel() {
        return channel;
    }

    public Envelope getEnvelope() {
        return envelope;
    }

    public ChannelHandler getChannelHandler() {
        return channelHandler;
    }

}
