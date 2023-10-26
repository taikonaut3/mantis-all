package io.github.astro.mantis.transport.netty;

import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.channel.AbstractChannel;
import io.netty.channel.Channel;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class NettyChannel extends AbstractChannel {

    private static final ConcurrentMap<Channel, NettyChannel> CHANNEL_MAP = new ConcurrentHashMap<>();

    private final Channel channel;

    public NettyChannel(InetSocketAddress remoteAddress, Channel channel) {
        super(remoteAddress);
        this.channel = channel;
    }

    public static NettyChannel getChannel(Channel channel) {
        NettyChannel nettyChannel = CHANNEL_MAP.get(channel);
        if (nettyChannel == null) {
            InetSocketAddress remoteAddress = (InetSocketAddress) channel.remoteAddress();
            nettyChannel = new NettyChannel(remoteAddress, channel);
            CHANNEL_MAP.put(channel, nettyChannel);
        }
        return nettyChannel;
    }

    public static void removeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isActive()) {
                channel.close();
            }
            CHANNEL_MAP.remove(channel);
        }
    }

    @Override
    public void doClose() throws NetWorkException {
        try {
            channel.close();
        } catch (Throwable e) {
            throw new NetWorkException(e);
        }
    }

    @Override
    public boolean isActive() {
        return channel.isActive();
    }

    @Override
    public void send(Object message) throws NetWorkException {
        if (isActive()) {
            channel.writeAndFlush(message);
        } else {
            if (message instanceof Request request) {
                System.out.println(request.getHeader().getExtendData("url"));
            }
        }
    }
}
