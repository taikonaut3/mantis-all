package io.github.astro.mantis.transport.endpoint;

import java.net.InetSocketAddress;

public class EndpointAdapter implements Endpoint {

    protected InetSocketAddress address;

    protected EndpointAdapter(InetSocketAddress address) {
        this.address = address;
    }

    protected EndpointAdapter(String ip, int port) {
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
