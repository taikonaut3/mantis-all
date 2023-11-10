package io.github.astro.mantis.transport.endpoint;

import io.github.astro.mantis.common.util.NetUtils;

import java.net.InetSocketAddress;

/**
 * Represents a network endpoint,including Host and Port information.
 */
public interface Endpoint {

    /**
     * Gets the host name of the endpoint.
     *
     * @return
     */
    String getHost();

    /**
     * Gets the port number of the endpoint.
     *
     * @return
     */
    int getPort();

    /**
     * Converts the endpoint to an InetSocketAddress object.
     *
     * @return
     */
    InetSocketAddress toInetSocketAddress();

    /**
     * Converts the endpoint to "host:port".
     *
     * @return
     */
    default String getAddress() {
        return NetUtils.getAddress(getHost(), getPort());
    }

}