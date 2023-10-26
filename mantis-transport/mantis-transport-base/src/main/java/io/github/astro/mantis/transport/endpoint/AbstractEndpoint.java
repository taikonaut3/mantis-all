package io.github.astro.mantis.transport.endpoint;

import java.net.InetSocketAddress;

public class AbstractEndpoint implements Endpoint {

    protected InetSocketAddress address;

    protected AbstractEndpoint(InetSocketAddress address) {
        this.address = address;
    }

    protected AbstractEndpoint(String ip, int port) {
        this.address = new InetSocketAddress(ip, port);
    }

    @Override
    public String getHost() {
        return address.getHostString();
    }

    @Override
    public int getPort() {
        return address.getPort();
    }

    @Override
    public InetSocketAddress toInetSocketAddress() {
        return address;
    }

}
