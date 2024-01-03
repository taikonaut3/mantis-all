package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.event.ServerHandlerExceptionEvent;

/**
 * +---------------------------------------ServerChannelHandlerChain---------------------------------------+
 * +--------------------------------+--------------------------------------+-------------------------------+
 * |{@link ServerHeartBeatChannelHandler} -> {@link ServerConvertChannelHandler} -> {@link ServerChannelHandler} |
 * +--------------------------------+--------------------------------------+-------------------------------+
 */
public class ServerDefaultChannelHandlerChain extends DefaultChannelHandlerChain {

    public ServerDefaultChannelHandlerChain(MantisApplication mantisApplication) {
        super(mantisApplication);
        addLast(new ServerHeartBeatChannelHandler());
        addLast(new ServerConvertChannelHandler(mantisApplication));
        addLast(new ServerChannelHandler(mantisApplication));
    }

    @Override
    public void caught(Channel channel, Throwable cause) throws RpcException {
        super.caught(channel, cause);
        getEventDispatcher().dispatchEvent(new ServerHandlerExceptionEvent(channel,cause));
    }

}
