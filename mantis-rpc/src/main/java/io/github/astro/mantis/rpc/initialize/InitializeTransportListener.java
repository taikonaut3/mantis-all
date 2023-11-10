package io.github.astro.mantis.rpc.initialize;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.configuration.spi.LoadedListener;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.rpc.listener.ChannelHandlerExceptionEventListener;
import io.github.astro.mantis.rpc.listener.HeartBeatEventListener;
import io.github.astro.mantis.rpc.listener.RequestEventListener;
import io.github.astro.mantis.rpc.listener.ResponseEventListener;
import io.github.astro.mantis.transport.AbstractTransporter;
import io.github.astro.mantis.transport.Transporter;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;
import io.github.astro.mantis.transport.client.ClientChannelHandlerChain;
import io.github.astro.mantis.transport.event.ChannelHandlerExceptionEvent;
import io.github.astro.mantis.transport.event.HeartBeatEvent;
import io.github.astro.mantis.transport.event.RequestEvent;
import io.github.astro.mantis.transport.event.ResponseEvent;
import io.github.astro.mantis.transport.server.ServerChannelHandlerChain;

/**
 * Create Server And Client Internal ChannelHandler,Register Internal NetWork Event listener
 *
 * @see io.github.astro.mantis.transport.channel.ChannelHandlerChain
 * server: {@link ServerChannelHandlerChain}
 * client: {@link ClientChannelHandlerChain}
 */
public class InitializeTransportListener implements LoadedListener<Transporter> {

    private final MantisApplication application;

    public InitializeTransportListener(MantisApplication application) {
        this.application = application;
    }

    @Override
    public void listen(Transporter service) {
        if (service instanceof AbstractTransporter transporter) {
            DefaultChannelHandlerChain serverHandlerChain = new ServerChannelHandlerChain(application);
            DefaultChannelHandlerChain clientHandlerChain = new ClientChannelHandlerChain(application);
            transporter.setServerChannelHandler(serverHandlerChain);
            transporter.setClientChannelHandler(clientHandlerChain);
            ExtensionLoader.loadServices(EventDispatcher.class).forEach(eventDispatcher -> {
                        eventDispatcher.addListener(HeartBeatEvent.class, new HeartBeatEventListener());
                        eventDispatcher.addListener(ChannelHandlerExceptionEvent.class, new ChannelHandlerExceptionEventListener());
                        eventDispatcher.addListener(RequestEvent.class, new RequestEventListener(application));
                        eventDispatcher.addListener(ResponseEvent.class, new ResponseEventListener(application));
                    }
            );
        }
    }

}
