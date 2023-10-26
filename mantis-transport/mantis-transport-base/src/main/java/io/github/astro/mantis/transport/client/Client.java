package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.exception.ConnectException;
import io.github.astro.mantis.transport.Closeable;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.endpoint.Endpoint;

public interface Client extends Endpoint, Closeable {

    void connect() throws ConnectException;

    Channel getChannel();

}