package io.github.astro.mantis.transport.channel;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.transport.endpoint.EndpointAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractChannel extends EndpointAdapter implements Channel {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChannel.class);

    private final Map<String, Object> attributeMap = new ConcurrentHashMap<>();

    protected AbstractChannel(InetSocketAddress address) {
        super(address);
    }

    @Override
    public void close() throws NetWorkException {
        doClose();
        attributeMap.clear();
        logger.debug("Closed {}", this);
    }

    protected abstract void doClose() throws NetWorkException;

    @Override
    public InetSocketAddress getRemoteAddress() {
        return toInetSocketAddress();
    }

    @Override
    public Object getAttribute(String key) {
        return attributeMap.get(key);
    }

    @Override
    public Object getAttribute(String key, Object defaultValue) {
        Object result = getAttribute(key);
        if (result == null) {
            attributeMap.put(key, defaultValue);
            result = defaultValue;
        }
        return result;
    }

    @Override
    public void setAttribute(String key, Object value) {
        attributeMap.put(key, value);
    }

    @Override
    public void removeAttribute(String key) {
        attributeMap.remove(key);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " connect to " + NetUtils.getAddress(toInetSocketAddress());
    }

}
