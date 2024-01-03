package io.github.astro.mantis.transport.netty;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.transport.CompositeTransport;
import io.github.astro.mantis.transport.client.EndpointClient;
import io.github.astro.mantis.transport.netty.client.NettyEndpointClient;
import io.github.astro.mantis.transport.netty.server.NettyEndpointServer;
import io.github.astro.mantis.transport.server.EndpointServer;

import static io.github.astro.mantis.common.constant.KeyValues.Transport.NETTY;

@ServiceProvider(NETTY)
public final class NettyTransport extends CompositeTransport {

    @Override
    protected EndpointClient createClient(URL url, Codec codec) {
        return new NettyEndpointClient(url, clientChannelHandler, codec);
    }

    @Override
    protected EndpointServer createServer(URL url, Codec codec) {
        return new NettyEndpointServer(url, serverChannelHandler, codec);
    }

}
