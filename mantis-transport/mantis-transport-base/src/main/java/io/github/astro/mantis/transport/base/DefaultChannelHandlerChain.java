package io.github.astro.mantis.transport.base;

import io.github.astro.mantis.Envelope;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.channel.ChannelHandlerChain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultChannelHandlerChain extends ChannelHandlerAdapter implements ChannelHandlerChain {

    private static final Logger logger = LoggerFactory.getLogger(DefaultChannelHandlerChain.class);

    private final List<ChannelHandler> channelHandlers = new LinkedList<>();

    private final Map<String, Channel> channels = new ConcurrentHashMap<>();

    public DefaultChannelHandlerChain(MantisApplication mantisApplication) {
        super(mantisApplication);
    }

    @Override
    public ChannelHandlerChain addLast(ChannelHandler channelHandler) {
        channelHandlers.add(channelHandler);
        return this;
    }

    @Override
    public ChannelHandler[] getChannelHandlers() {
        return channelHandlers.toArray(ChannelHandler[]::new);
    }

    @Override
    public Channel[] getChannels() {
        return channels.values().toArray(Channel[]::new);
    }

    @Override
    public void connected(Channel channel) throws RpcException {
        channels.putIfAbsent(NetUtils.getAddress(channel.getRemoteAddress()), channel);
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.connected(channel);
        }
        logger.debug("Connected {}", channel);
    }

    @Override
    public void disconnected(Channel channel) throws RpcException {
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.disconnected(channel);
        }
        logger.debug("Disconnected {}", channel);
    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {
        if (message instanceof Envelope envelope) {
            channel.setAttribute(Key.URL, envelope.getUrl());
        }
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.received(channel, message);
        }
    }

    @Override
    public void caught(Channel channel, Throwable cause) throws RpcException {
        channels.remove(NetUtils.getAddress(channel.getRemoteAddress()));
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.caught(channel, cause);
        }
        logger.error("Channel has Error", cause);
    }

    @Override
    public void heartBeat(Channel channel, Object event) {
        for (ChannelHandler channelHandler : channelHandlers) {
            channelHandler.heartBeat(channel, event);
        }
    }
}
