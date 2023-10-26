package io.github.astro.mantis.transport.channel;

import io.github.astro.mantis.common.exception.NetWorkException;

public interface ChannelHandler {

    default void connected(Channel channel) throws NetWorkException {
    }

    default void disconnected(Channel channel) throws NetWorkException {
        channel.close();
    }

    default void received(Channel channel, Object message) throws NetWorkException {

    }

    default void sent(Channel channel, Object message) throws NetWorkException {
        channel.send(message);
    }

    default void caught(Channel channel, Throwable cause) throws NetWorkException {
        channel.close();
        cause.printStackTrace();
    }

}