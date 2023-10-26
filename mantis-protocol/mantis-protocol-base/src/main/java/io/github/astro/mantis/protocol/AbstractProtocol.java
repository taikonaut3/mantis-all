package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.MessageType;
import io.github.astro.mantis.common.constant.ProtocolType;
import io.github.astro.mantis.common.constant.ServiceType;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.invoke.Result;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.Transporter;
import io.github.astro.mantis.transport.client.Client;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.event.ResponseFuture;
import io.github.astro.mantis.transport.event.ResponseResult;
import io.github.astro.mantis.transport.header.Header;
import io.github.astro.mantis.transport.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractProtocol implements Protocol {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProtocol.class);
    private static final AtomicLong INCREASE = new AtomicLong(0);
    private final Map<String, Server> servers = new ConcurrentHashMap<>();
    private final Map<String, Client> clients = new ConcurrentHashMap<>();
    private final Map<String, Codec> codecs = new HashMap<>();
    protected ProtocolType protocolType;

    public AbstractProtocol(ProtocolType protocolType) {
        this.protocolType = protocolType;
    }

    @Override
    public Result send(URL url, Invocation invocation) {
        boolean isAsync = url.getBooleanParameter(Key.IS_ASYNC);
        Request request = buildRequest(url, invocation);
        ResponseFuture future = new ResponseFuture(url, invocation, request.getId());
        Client client = openClient(url);
        // request
        client.getChannel().send(request);
        logger.debug("Request: {}", url);
        return new ResponseResult(future, isAsync);
    }

    private Request buildRequest(URL url, Invocation invocation) {
        url.addParameter(Key.MESSAGE_TYPE, String.valueOf(MessageType.REQUEST.getType()));
        Header header = createHeader(url);
        header.addExtendData(Key.URL, url.toString());
        header.addExtendData(Key.UNIQUE_ID, String.valueOf(INCREASE.getAndIncrement()));
        return new Request(header, invocation);
    }

    @Override
    public Client openClient(URL url) {
        String key = url.getAddress();
        Client client = clients.get(key);
        if (client == null) {
            synchronized (this) {
                if (clients.get(key) == null) {
                    Transporter transporter = ServiceProviderLoader.loadService(Transporter.class,
                            url.getParameter(Key.TRANSPORT, ServiceType.Transport.NETTY));
                    client = transporter.connect(url, getCodec(url, Request.class, Response.class));
                    clients.put(key, client);
                }
            }
        } else if (!client.isActive()) {
            client.connect();
        }
        return client;
    }

    @Override
    public Server openServer(URL url) {
        String key = url.getAddress();
        Server server = servers.get(key);
        if (server == null) {
            synchronized (this) {
                if (servers.get(key) == null) {
                    Transporter transporter = ServiceProviderLoader.loadService(Transporter.class,
                            url.getParameter(Key.TRANSPORT, ServiceType.Transport.NETTY));
                    server = transporter.bind(url, getCodec(url, Response.class, Request.class));
                    servers.put(key, server);
                }
            }
        } else if (!server.isActive()) {
            server.bind();
        }
        return server;
    }

    @Override
    public Codec getCodec(URL url, Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo) {
        String key = getType().getName() + ":" + getType().getVersion() + ":" + encodeFrom.getName() + "|" + decodeTo.getName();
        Codec codec = codecs.get(key);
        if (codec == null) {
            synchronized (this) {
                if (codecs.get(key) == null) {
                    codec = createCodec(url, encodeFrom, decodeTo);
                    codecs.put(key, codec);
                }
            }
        }
        return codec;
    }

    @Override
    public ProtocolType getType() {
        return protocolType;
    }

    @Override
    public Server[] getServers() {
        return servers.values().toArray(new Server[0]);
    }

    @Override
    public Client[] getClients() {
        return clients.values().toArray(new Client[0]);
    }

    protected abstract Codec createCodec(URL url, Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo);

}
