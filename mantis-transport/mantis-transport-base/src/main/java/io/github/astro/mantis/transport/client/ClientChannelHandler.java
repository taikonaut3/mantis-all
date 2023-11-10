package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerAdapter;
import io.github.astro.mantis.transport.event.ResponseEvent;

public final class ClientChannelHandler extends DefaultChannelHandlerAdapter {

    public ClientChannelHandler(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Response response) {
            getEventDispatcher(response).dispatchEvent(new ResponseEvent(response));
        }
    }

}
