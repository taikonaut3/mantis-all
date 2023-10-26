package io.github.astro.mantis.transport.channel;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.transport.endpoint.AbstractEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public abstract class AbstractChannel extends AbstractEndpoint implements Channel {
    private static final Logger logger = LoggerFactory.getLogger(AbstractChannel.class);

    protected AbstractChannel(InetSocketAddress address) {
        super(address.getHostString(), address.getPort());
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        return toInetSocketAddress();
    }

    @Override
    public Object getAttribute(String key) {
        return null;
    }

    @Override
    public void setAttribute(String key, Object value) {

    }

    @Override
    public void removeAttribute(String key) {

    }

    @Override
    public void close() throws NetWorkException {
        logger.debug("Close {}", this);
        doClose();
    }

    protected abstract void doClose() throws NetWorkException;

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " connect to " + NetUtils.getAddress(toInetSocketAddress());
    }
}
