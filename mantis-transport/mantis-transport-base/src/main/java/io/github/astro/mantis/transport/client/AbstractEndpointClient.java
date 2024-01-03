package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.exception.ConnectException;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.endpoint.EndpointAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractEndpointClient extends EndpointAdapter implements EndpointClient {

    private static final Logger logger = LoggerFactory.getLogger(AbstractEndpointClient.class);

    public int connectTimeout;

    protected Channel channel;

    protected ChannelHandler channelHandler;

    protected Codec codec;

    protected URL url;

    private boolean isInit = false;

    protected AbstractEndpointClient(URL url, ChannelHandler channelHandler, Codec codec) throws ConnectException {
        super(url.getHost(), url.getPort());
        this.url = url;
        this.channelHandler = channelHandler;
        this.codec = codec;
        try {
            connect();
            logger.debug("Create {} is Successful,Connect to remoteAddress: {} for Protocol({})", this.getClass().getSimpleName(), getAddress(), url.getProtocol());
        } catch (Throwable e) {
            logger.error("Create Client is Failed,Connect to remoteAddress:" + getAddress(), e);
            throw new ConnectException("Create Client is Failed,Connect to remoteAddress :" + getAddress(), e);
        }
    }

    @Override
    public void connect() throws ConnectException {
        if (isActive()) {
            return;
        }
        // When reconnecting, there is no need to initialize again
        if (!isInit) {
            init();
            isInit = true;
        }
        doConnect();
    }

    @Override
    public void close() throws NetWorkException {
        try {
            channel.close();
        } catch (Throwable e) {
            throw new NetWorkException(e);
        } finally {
            doClose();
        }
    }

    protected abstract void init() throws ConnectException;

    protected abstract void doConnect() throws ConnectException;

    protected abstract void doClose() throws NetWorkException;

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " connect to " + NetUtils.getAddress(toInetSocketAddress());
    }

}
