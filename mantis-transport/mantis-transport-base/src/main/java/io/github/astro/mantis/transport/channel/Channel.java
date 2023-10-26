package io.github.astro.mantis.transport.channel;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.transport.Closeable;
import io.github.astro.mantis.transport.endpoint.Endpoint;

import java.net.InetSocketAddress;

public interface Channel extends Endpoint, Closeable {

    InetSocketAddress getRemoteAddress();

    void send(Object message) throws NetWorkException;

    Object getAttribute(String key);

    void setAttribute(String key, Object value);

    void removeAttribute(String key);

}