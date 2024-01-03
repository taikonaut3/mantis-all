package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.event.AbstractEvent;
import io.github.astro.mantis.transport.channel.Channel;

public abstract class ChannelHandlerExceptionEvent extends AbstractEvent<Throwable> {

    private final Channel channel;



    public ChannelHandlerExceptionEvent(Channel channel, Throwable cause) {
        super(cause);
        this.channel = channel;
    }
    public Channel getChannel() {
        return channel;
    }

}
