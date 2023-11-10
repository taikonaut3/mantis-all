package io.github.astro.mantis.transport.channel;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.transport.Closeable;
import io.github.astro.mantis.transport.endpoint.Endpoint;

import java.net.InetSocketAddress;

/**
 * Network Channel
 */
public interface Channel extends Endpoint, Closeable {

    /**
     * Gets remote address associated with the channel.
     *
     * @return
     */
    InetSocketAddress getRemoteAddress();

    /**
     * Sends the given message through the channel.
     *
     * @param message
     * @throws NetWorkException
     */
    void send(Object message) throws NetWorkException;

    /**
     * Gets Attribute
     *
     * @param key
     * @return
     */
    Object getAttribute(String key);

    /**
     * Gets Attribute if null use defaultValue and sets
     *
     * @param key
     * @param defaultValue
     * @return
     */
    Object getAttribute(String key, Object defaultValue);

    /**
     * Sets Attribute
     *
     * @param key
     * @param value
     */
    void setAttribute(String key, Object value);

    /**
     * Remove Attribute By Key
     *
     * @param key
     */
    void removeAttribute(String key);

}