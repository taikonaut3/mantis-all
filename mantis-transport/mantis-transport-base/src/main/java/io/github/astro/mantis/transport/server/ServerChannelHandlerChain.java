package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;

/**
 * +---------------------------------------ServerChannelHandlerChain---------------------------------------+
 * +--------------------------------+--------------------------------------+-------------------------------+
 * |{@link ServerHeartBeatChannelHandler} -> {@link ServerConvertChannelHandler} -> {@link ServerChannelHandler} |
 * +--------------------------------+--------------------------------------+-------------------------------+
 */
public class ServerChannelHandlerChain extends DefaultChannelHandlerChain {

    public ServerChannelHandlerChain(MantisApplication mantisApplication) {
        super(mantisApplication);
        addLast(new ServerHeartBeatChannelHandler());
        addLast(new ServerConvertChannelHandler(mantisApplication));
        addLast(new ServerChannelHandler(mantisApplication));
    }

    @Override
    public EventDispatcher getEventDispatcher(Envelope envelope) {
        String eventDispatcherKey = envelope.getUrl().getParameter(Key.SERVER_EVENT_DISPATCHER, Constant.DEFAULT_EVENT_DISPATCHER);
        return ExtensionLoader.loadService(EventDispatcher.class, eventDispatcherKey);
    }

}
