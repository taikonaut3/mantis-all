package io.github.astro.mantis.transport;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.client.EndpointClient;
import io.github.astro.mantis.transport.client.ClientTransport;
import io.github.astro.mantis.transport.server.EndpointServer;
import io.github.astro.mantis.transport.server.ServerTransport;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/4 16:49
 */
public abstract class CompositeTransport implements ClientTransport, ServerTransport {

    private final Map<String, EndpointClient> clients = new ConcurrentHashMap<>();

    private final Map<String, EndpointServer> servers = new ConcurrentHashMap<>();

    protected ChannelHandler serverChannelHandler;

    protected ChannelHandler clientChannelHandler;

    @Override
    public EndpointServer bind(URL url, Codec codec) throws NetWorkException {
        String key = url.getAddress();
        EndpointServer server = servers.get(key);
        if (server == null) {
            synchronized (this) {
                if (servers.get(key) == null) {
                    server = createServer(url, codec);
                    servers.put(key, server);
                }
            }
        } else if (!server.isActive()) {
            server.bind();
        }
        return server;
    }

    @Override
    public Client connect(URL url, Codec codec) throws NetWorkException {
        String key = url.getAddress();
        EndpointClient client = clients.get(key);
        if (client == null) {
            synchronized (this) {
                if (clients.get(key) == null) {
                    client = createClient(url, codec);
                    clients.put(key, client);
                }
            }
        } else if (!client.isActive()) {
            client.connect();
        }
        return client;
    }

    protected abstract EndpointClient createClient(URL url, Codec codec);

    protected abstract EndpointServer createServer(URL url, Codec codec);

    public void setServerChannelHandler(ChannelHandler serverChannelHandler) {
        this.serverChannelHandler = serverChannelHandler;
    }

    public void setClientChannelHandler(ChannelHandler clientChannelHandler) {
        this.clientChannelHandler = clientChannelHandler;
    }

    @Override
    public ChannelHandler getServerChannelHandler() {
        return serverChannelHandler;
    }

    @Override
    public ChannelHandler getClientChannelHandler() {
        return clientChannelHandler;
    }

    @Override
    public EndpointServer[] getServers() {
        return servers.values().toArray(EndpointServer[]::new);
    }

    @Override
    public Client[] getClients() {
        return clients.values().toArray(EndpointClient[]::new);
    }

}
