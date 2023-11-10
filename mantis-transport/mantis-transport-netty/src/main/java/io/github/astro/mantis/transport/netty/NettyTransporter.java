package io.github.astro.mantis.transport.netty;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.transport.AbstractTransporter;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.netty.client.NettyClient;
import io.github.astro.mantis.transport.netty.server.NettyServer;
import io.github.astro.mantis.transport.server.Server;

import static io.github.astro.mantis.common.constant.KeyValues.Transport.NETTY;

@ServiceProvider(NETTY)
public final class NettyTransporter extends AbstractTransporter {

    @Override
    protected Client createClient(URL url, Codec codec) {
        return new NettyClient(url, clientChannelHandler, codec);
    }

    @Override
    protected Server createServer(URL url, Codec codec) {
        return new NettyServer(url, serverChannelHandler, codec);
    }

}
