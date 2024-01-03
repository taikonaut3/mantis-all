package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.event.AbstractEvent;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.util.ProtocolUtil;

public class RequestEvent extends AbstractEvent<Request> {

    private final Channel channel;

    private Object body;

    public RequestEvent(Request request, Channel channel) {
        super(request);
        this.channel = channel;
        this.body = ProtocolUtil.parseBody(request);
    }

    public Channel getChannel() {
        return channel;
    }

    public Object getBody() {
        return body;
    }

}
