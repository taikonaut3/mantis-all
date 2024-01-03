package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;
import io.github.astro.mantis.transport.channel.ChannelHandler;

import static io.github.astro.mantis.common.constant.KeyValues.Transport.NETTY;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/4 15:53
 */
@ServiceInterface(NETTY)
public interface ServerTransport {

    /**
     * Binds a Server to the URL with the given codec.
     *
     * @param url   The URL to bind the server and increase extensibility.
     * @param codec The codec to be used for message encoding and decoding.
     * @return The bound server object.
     * @throws NetWorkException
     */
    EndpointServer bind(URL url, Codec codec) throws NetWorkException;

    /**
     * Get all the bound Servers
     *
     * @return
     */
    EndpointServer[] getServers();


    void setServerChannelHandler(ChannelHandler channelHandler);

    ChannelHandler getServerChannelHandler();

}
