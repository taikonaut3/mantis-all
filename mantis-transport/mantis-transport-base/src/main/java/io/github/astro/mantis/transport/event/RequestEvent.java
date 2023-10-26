package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.event.EventType;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.channel.Channel;

public class RequestEvent extends EnvelopeEvent<Request> {
    private final Channel channel;

    public RequestEvent(Request request, Channel channel) {
        super(request, EventType.Request);
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

}
