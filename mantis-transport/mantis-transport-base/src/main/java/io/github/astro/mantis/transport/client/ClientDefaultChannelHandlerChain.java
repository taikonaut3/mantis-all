package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.ClientHandlerExceptionEvent;

/**
 * +------------------------------------------ClientChannelHandlerChain------------------------------------------+
 * +----------------------------------+----------------------------------------+---------------------------------+
 * |{@link ClientHeartBeatChannelHandler} -> {@link ClientConvertChannelHandler} -> {@link ClientChannelHandler} |
 * +----------------------------------+----------------------------------------+---------------------------------+
 */
public class ClientDefaultChannelHandlerChain extends DefaultChannelHandlerChain {

    public ClientDefaultChannelHandlerChain(MantisApplication mantisApplication) {
        super(mantisApplication);
        addLast(new ClientHeartBeatChannelHandler());
        addLast(new ClientConvertChannelHandler(mantisApplication));
        addLast(new ClientChannelHandler(mantisApplication));
    }

    @Override
    public void caught(Channel channel, Throwable cause) throws RpcException {
        super.caught(channel, cause);
        getEventDispatcher().dispatchEvent(new ClientHandlerExceptionEvent(channel,cause));
    }

}
