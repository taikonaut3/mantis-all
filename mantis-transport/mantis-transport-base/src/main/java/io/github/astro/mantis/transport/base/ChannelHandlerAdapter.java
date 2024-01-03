package io.github.astro.mantis.transport.base;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.MantisApplication;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.event.EventDispatcher;
import io.github.astro.mantis.transport.channel.Channel;
import io.github.astro.mantis.transport.channel.ChannelHandler;

public class ChannelHandlerAdapter implements ChannelHandler {

    protected MantisApplication mantisApplication;


    protected ChannelHandlerAdapter() {

    }

    protected ChannelHandlerAdapter(MantisApplication mantisApplication) {
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

    @Override
    public Channel[] getChannels() {
        return new Channel[0];
    }

    public EventDispatcher getEventDispatcher(){
        return ExtensionLoader.loadService(EventDispatcher.class,mantisApplication.getAppConfiguration().getEventDispatcher());
    }
}
