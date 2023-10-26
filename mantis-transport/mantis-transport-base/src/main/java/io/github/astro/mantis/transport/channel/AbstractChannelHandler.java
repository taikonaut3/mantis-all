package io.github.astro.mantis.transport.channel;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.common.util.NetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AbstractChannelHandler implements ChannelHandler {

    private static final Logger logger = LoggerFactory.getLogger(AbstractChannelHandler.class);

    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    @Override
    public void connected(Channel channel) throws NetWorkException {
        channels.putIfAbsent(NetUtils.getAddress(channel.getRemoteAddress()), channel);
    }

    @Override
    public void disconnected(Channel channel) throws NetWorkException {

    }

    @Override
    public void caught(Channel channel, Throwable cause) throws NetWorkException {
        channels.remove(NetUtils.getAddress(channel.getRemoteAddress()));
        ChannelHandler.super.caught(channel, cause);
    }

    public Map<String, Channel> getChannels() {
        return channels;
    }
}
