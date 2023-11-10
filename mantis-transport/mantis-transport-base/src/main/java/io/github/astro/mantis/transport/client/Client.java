package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.exception.ConnectException;
import io.github.astro.mantis.transport.Closeable;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.endpoint.Endpoint;

/**
 * Network client
 */
public interface Client extends Endpoint, Closeable {

    /**
     * Connects to the server.
     *
     * @throws ConnectException
     */
    void connect() throws ConnectException;

    /**
     * Returns the channel associated.
     *
     * @return
     */
    Channel getChannel();

}