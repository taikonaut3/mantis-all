package io.github.astro.mantis.rpc.initialize;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.LoadedListener;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;
import io.github.astro.mantis.transport.client.ClientDefaultChannelHandlerChain;
import io.github.astro.mantis.transport.client.ClientTransport;
import io.github.astro.mantis.transport.server.ServerDefaultChannelHandlerChain;

/**
 * Create Server And Client Internal ChannelHandler,Register Internal NetWork Event listener
 *
 * @see io.github.astro.mantis.transport.channel.ChannelHandlerChain
 * server: {@link ServerDefaultChannelHandlerChain}
 * client: {@link ClientDefaultChannelHandlerChain}
 */
public class InitializeClientTransportListener implements LoadedListener<ClientTransport> {

    private final MantisApplication application;

    public InitializeClientTransportListener(MantisApplication application) {
        this.application = application;
    }

    @Override
    public void listen(ClientTransport clientTransport) {
        DefaultChannelHandlerChain clientHandlerChain = new ClientDefaultChannelHandlerChain(application);
        clientTransport.setClientChannelHandler(clientHandlerChain);

    }

}
