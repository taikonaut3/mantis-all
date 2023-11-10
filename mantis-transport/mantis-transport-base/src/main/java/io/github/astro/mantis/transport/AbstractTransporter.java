package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.ChannelHandlerChain;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.server.Server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransporter implements Transporter {

    private final Map<String, Client> clients = new ConcurrentHashMap<>();

    private final Map<String, Server> servers = new ConcurrentHashMap<>();

    protected ChannelHandlerChain serverChannelHandler;

    protected ChannelHandlerChain clientChannelHandler;

    @Override
    public Server bind(URL url, Codec codec) throws NetWorkException {
        String key = url.getAddress();
        Server server = servers.get(key);
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
        Client client = clients.get(key);
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

    protected abstract Client createClient(URL url, Codec codec);

    protected abstract Server createServer(URL url, Codec codec);

    @Override
    public ChannelHandlerChain getServerChannelHandler() {
        return serverChannelHandler;
    }

    public void setServerChannelHandler(ChannelHandlerChain serverChannelHandler) {
        this.serverChannelHandler = serverChannelHandler;
    }

    @Override
    public ChannelHandlerChain getClientChannelHandler() {
        return clientChannelHandler;
    }

    public void setClientChannelHandler(ChannelHandlerChain clientChannelHandler) {
        this.clientChannelHandler = clientChannelHandler;
    }

    @Override
    public Server[] getServers() {
        return servers.values().toArray(Server[]::new);
    }

    @Override
    public Client[] getClients() {
        return clients.values().toArray(Client[]::new);
    }

}
