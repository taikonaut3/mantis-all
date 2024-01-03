package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;
import io.github.astro.mantis.transport.channel.ChannelHandler;

import static io.github.astro.mantis.common.constant.KeyValues.Transport.NETTY;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/4 15:54
 */
@ServiceInterface(NETTY)
public interface ClientTransport {

    /**
     * Connects to the server specified by the URL with the given codec.
     *
     * @param url   The URL of the server to connect and Increase extensibility.
     * @param codec The codec to be used for message encoding and decoding.
     * @return The connected client object.
     * @throws NetWorkException
     */
    Client connect(URL url, Codec codec) throws NetWorkException;

    /**
     * Get all the connected clients.
     *
     * @return
     */
    Client[] getClients();

    void setClientChannelHandler(ChannelHandler channelHandler);

    ChannelHandler getClientChannelHandler();

}
