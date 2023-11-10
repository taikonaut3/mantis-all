package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.exception.BindException;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.channel.ChannelHandlerChain;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.endpoint.EndpointAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public abstract class AbstractServer extends EndpointAdapter implements Server {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServer.class);

    protected final Channel[] channels;

    protected int soBacklog;

    protected ChannelHandler channelHandler;

    protected Codec codec;

    protected URL url;

    private boolean isInit = false;

    protected AbstractServer(URL url, ChannelHandlerChain channelHandler, Codec codec) throws BindException {
        super(url.getHost(), url.getPort());
        this.url = url;
        this.channelHandler = channelHandler;
        this.codec = codec;
        this.channels = channelHandler.getChannels();
        try {
            bind();
            logger.debug("Create {} is Successful,Bind address: {} for Protocol({})", this.getClass().getSimpleName(), getAddress(), url.getProtocol());
        } catch (Throwable e) {
            logger.error("Create Server is Failed,Bind address:" + getAddress(), e);
            throw new BindException("Create Server is Failed,Bind address :" + getAddress(), e);
        }
    }

    @Override
    public void bind() throws BindException {
        if (isActive()) {
            return;
        }
        if (!isInit) {
            init();
            isInit = true;
        }
        doBind();
    }

    @Override
    public void close() throws NetWorkException {
        for (Channel channel : getChannels()) {
            try {
                channel.close();
            } catch (Throwable e) {
                throw new NetWorkException(e);
            }
        }
        try {
            doClose();
        } catch (Throwable e) {
            throw new NetWorkException(e);
        }
    }

    protected abstract void init() throws BindException;

    protected abstract void doBind() throws BindException;

    protected abstract void doClose() throws NetWorkException;

    @Override
    public Channel[] getChannels() {
        return channels;
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
        for (Channel channel : channels) {
            if (channel.getAddress().equals(NetUtils.getAddress(remoteAddress))) {
                return channel;
            }
        }
        return null;
    }

}
