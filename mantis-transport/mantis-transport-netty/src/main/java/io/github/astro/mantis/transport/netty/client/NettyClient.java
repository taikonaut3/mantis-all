package io.github.astro.mantis.transport.netty.client;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.exception.ConnectException;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.client.AbstractClient;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.netty.NettyChannel;
import io.github.astro.mantis.transport.netty.codec.NettyCodec;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.TimeUnit;

public final class NettyClient extends AbstractClient {

    private static final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(Constant.DEFAULT_IO_THREADS, new DefaultThreadFactory("NettyClientWorker", true));

    private Bootstrap bootstrap;

    private Channel channel;

    public NettyClient(URL url, ChannelHandler channelHandler, Codec codec) throws ConnectException {
        super(url, channelHandler, codec);
    }

    @Override
    protected void init() throws ConnectException {
        bootstrap = new Bootstrap();
        final NettyClientChannelHandler handler = new NettyClientChannelHandler(channelHandler);
        initBootStrap(handler);
    }

    private void initBootStrap(io.netty.channel.ChannelHandler handler) {
        bootstrap.group(nioEventLoopGroup)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, Math.min(connectTimeout, 3000))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        NettyCodec nettyCodec = new NettyCodec(codec);
                        ch.pipeline()
                                .addLast("decoder", nettyCodec.getDecoder())
                                .addLast("encoder", nettyCodec.getEncoder())
                                .addLast("handler", handler);
                    }
                });
    }

    @Override
    protected void doConnect() throws ConnectException {
        ChannelFuture future = bootstrap.connect(toInetSocketAddress());
        boolean ret = future.awaitUninterruptibly(connectTimeout, TimeUnit.MILLISECONDS);
        if (ret && future.isSuccess()) {
            channel = future.channel();
            super.channel = NettyChannel.getChannel(channel);
        } else if (future.cause() != null) {
            throw new ConnectException(future.cause());
        } else {
            throw new ConnectException("Unknown Exception");
        }
    }

    @Override
    protected void doClose() throws NetWorkException {
        try {
            nioEventLoopGroup.shutdownGracefully();
            NettyChannel.removeChannel(channel);
        } catch (Throwable e) {
            throw new NetWorkException(e);
        }
    }

    @Override
    public boolean isActive() {
        return channel.isActive();
    }

}
