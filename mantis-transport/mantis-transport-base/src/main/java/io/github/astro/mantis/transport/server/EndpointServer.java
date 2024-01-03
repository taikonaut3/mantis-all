package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.exception.BindException;
import io.github.astro.mantis.transport.Closeable;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.endpoint.Endpoint;

import java.net.InetSocketAddress;

/**
 * Network Server
 */
public interface EndpointServer extends Endpoint, Closeable {

    /**
     * Binds the server to the specified host and port.
     *
     * @throws BindException
     */
    void bind() throws BindException;

    /**
     * Gets all Channel associated with the Server.
     *
     * @return
     */
    Channel[] getChannels();

    /**
     * Gets Channel associated with the specified remote address.
     *
     * @param remoteAddress
     * @return
     */
    Channel getChannel(InetSocketAddress remoteAddress);

}