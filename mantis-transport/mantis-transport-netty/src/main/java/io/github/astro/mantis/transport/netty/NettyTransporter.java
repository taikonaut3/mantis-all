package io.github.astro.mantis.transport.netty;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.MantisBootStrap;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.MantisContext;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.transport.Transporter;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.client.ClientChannelHandler;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.netty.client.NettyClient;
import io.github.astro.mantis.transport.netty.server.NettyServer;
import io.github.astro.mantis.transport.server.Server;
import io.github.astro.mantis.transport.server.ServerChannelHandler;

import static io.github.astro.mantis.common.constant.ServiceType.Transport.NETTY;

@ServiceProvider(NETTY)
public final class NettyTransporter implements Transporter {

    private ChannelHandler clientHandler;

    private ChannelHandler serverHandler;

    public NettyTransporter() {
        MantisBootStrap mantisBootStrap = MantisContext.getCurrentBootStrap();
        serverHandler = new ServerChannelHandler(mantisBootStrap);
        clientHandler = new ClientChannelHandler(mantisBootStrap);
    }

    @Override
    public Server bind(URL url, Codec codec) throws NetWorkException {
        return new NettyServer(url, serverHandler, codec);
    }

    @Override
    public Client connect(URL url, Codec codec) throws NetWorkException {
        return new NettyClient(url, clientHandler, codec);
    }
}
