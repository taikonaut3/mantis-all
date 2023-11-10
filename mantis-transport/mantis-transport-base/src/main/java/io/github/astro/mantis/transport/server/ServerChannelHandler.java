package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerAdapter;
import io.github.astro.mantis.transport.event.RequestEvent;

public final class ServerChannelHandler extends DefaultChannelHandlerAdapter {

    public ServerChannelHandler(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public void received(Channel channel, Object message) {
        if (message instanceof Request request) {
            getEventDispatcher(request).dispatchEvent(new RequestEvent(request, channel));
        }
    }

}
