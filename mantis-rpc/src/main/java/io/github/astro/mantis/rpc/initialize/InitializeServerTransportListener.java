package io.github.astro.mantis.rpc.initialize;

import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.LoadedListener;
import io.github.astro.mantis.transport.base.DefaultChannelHandlerChain;
import io.github.astro.mantis.transport.server.ServerDefaultChannelHandlerChain;
import io.github.astro.mantis.transport.server.ServerTransport;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/5 14:14
 */
public class InitializeServerTransportListener implements LoadedListener<ServerTransport> {

    private final MantisApplication application;

    public InitializeServerTransportListener(MantisApplication application) {
        this.application = application;
    }

    @Override
    public void listen(ServerTransport serverTransport) {
        DefaultChannelHandlerChain serverHandlerChain = new ServerDefaultChannelHandlerChain(application);
        serverTransport.setServerChannelHandler(serverHandlerChain);
    }

}
