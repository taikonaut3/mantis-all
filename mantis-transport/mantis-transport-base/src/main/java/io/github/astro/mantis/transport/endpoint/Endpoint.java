package io.github.astro.mantis.transport.endpoint;

import io.github.astro.mantis.common.util.NetUtils;

import java.net.InetSocketAddress;

public interface Endpoint {

    String getHost();

    int getPort();

    InetSocketAddress toInetSocketAddress();

    default String getAddress() {
        return NetUtils.getAddress(getHost(), getPort());
    }
}