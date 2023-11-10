package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;
import io.github.astro.mantis.transport.channel.ChannelHandlerChain;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.server.Server;

import static io.github.astro.mantis.common.constant.KeyValues.Transport.NETTY;

/**
 * Network Transporter for creating Servers and Clients,
 * and Managing Server/Client instances.
 */
@ServiceInterface(NETTY)
public interface Transporter {

    /**
     * Binds a Server to the URL with the given codec.
     *
     * @param url   The URL to bind the server and increase extensibility.
     * @param codec The codec to be used for message encoding and decoding.
     * @return The bound server object.
     * @throws NetWorkException
     */
    Server bind(URL url, Codec codec) throws NetWorkException;

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
     * Get all the bound Servers
     *
     * @return
     */
    Server[] getServers();

    /**
     * Get all the connected clients.
     *
     * @return
     */
    Client[] getClients();

    /**
     * ChannelHandler chain for server-side Channels
     *
     * @return
     */
    ChannelHandlerChain getServerChannelHandler();

    /**
     * ChannelHandler chain for client-side Channels
     *
     * @return
     */
    ChannelHandlerChain getClientChannelHandler();

}