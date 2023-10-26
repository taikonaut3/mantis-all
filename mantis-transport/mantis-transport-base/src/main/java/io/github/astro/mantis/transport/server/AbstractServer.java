package io.github.astro.mantis.transport.server;

import io.github.astro.mantis.common.exception.BindException;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.AbstractChannelHandler;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.endpoint.AbstractEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Map;

public abstract class AbstractServer extends AbstractEndpoint implements Server {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServer.class);
    protected final Map<String, Channel> channels;
    protected ChannelHandler channelHandler;
    protected Codec codec;
    private boolean isInit = false;

    protected AbstractServer(URL url, ChannelHandler channelHandler, Codec codec) throws BindException {
        super(url.getHost(), url.getPort());
        this.channelHandler = channelHandler;
        this.codec = codec;
        this.channels = ((AbstractChannelHandler) channelHandler).getChannels();
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
    public Collection<Channel> getChannels() {
        return channels.values();
    }

    @Override
    public Channel getChannel(InetSocketAddress remoteAddress) {
        return channels.get(NetUtils.getAddress(remoteAddress));
    }

}
