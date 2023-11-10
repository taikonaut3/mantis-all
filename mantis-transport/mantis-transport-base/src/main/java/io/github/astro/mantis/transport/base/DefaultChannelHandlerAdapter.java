package io.github.astro.mantis.transport.base;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;

public class DefaultChannelHandlerAdapter implements ChannelHandler {

    protected MantisApplication mantisApplication;

    protected DefaultChannelHandlerChain channelHandlerChain;

    protected DefaultChannelHandlerAdapter() {

    }

    protected DefaultChannelHandlerAdapter(MantisApplication mantisApplication) {
        this.mantisApplication = mantisApplication;
    }

    @Override
    public void connected(Channel channel) throws RpcException {

    }

    @Override
    public void disconnected(Channel channel) throws RpcException {

    }

    @Override
    public void received(Channel channel, Object message) throws RpcException {

    }

    @Override
    public void caught(Channel channel, Throwable cause) throws RpcException {

    }

    public DefaultChannelHandlerChain getChannelHandlerChain() {
        return channelHandlerChain;
    }

    public void setChannelHandlerChain(DefaultChannelHandlerChain channelHandlerChain) {
        this.channelHandlerChain = channelHandlerChain;
    }

    protected EventDispatcher getEventDispatcher(Envelope envelope) {
        return channelHandlerChain.getEventDispatcher(envelope);
    }

}
