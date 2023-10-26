package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.exception.BindException;
import io.github.astro.mantis.transport.Closeable;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.endpoint.Endpoint;

import java.net.InetSocketAddress;
import java.util.Collection;

public interface Server extends Closeable, Endpoint {

    void bind() throws BindException;

    Collection<Channel> getChannels();

    Channel getChannel(InetSocketAddress remoteAddress);

}