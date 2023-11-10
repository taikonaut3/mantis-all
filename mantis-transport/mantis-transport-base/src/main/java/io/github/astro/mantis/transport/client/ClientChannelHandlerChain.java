package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;

/**
 * +---------------------------------------ClientChannelHandlerChain---------------------------------------+
 * +--------------------------------+--------------------------------------+-------------------------------+
 * |{@link ClientHeartBeatChannelHandler} -> {@link ClientConvertChannelHandler} -> {@link ClientChannelHandler} |
 * +--------------------------------+--------------------------------------+-------------------------------+
 */
public class ClientChannelHandlerChain extends DefaultChannelHandlerChain {

    public ClientChannelHandlerChain(MantisApplication mantisApplication) {
        super(mantisApplication);
        addLast(new ClientHeartBeatChannelHandler());
        addLast(new ClientConvertChannelHandler(mantisApplication));
        addLast(new ClientChannelHandler(mantisApplication));
    }

    @Override
    protected EventDispatcher getEventDispatcher(Envelope envelope) {
        String eventDispatcherKey = envelope.getUrl().getParameter(Key.EVENT_DISPATCHER, Constant.DEFAULT_EVENT_DISPATCHER);
        return ExtensionLoader.loadService(EventDispatcher.class, eventDispatcherKey);
    }

}
