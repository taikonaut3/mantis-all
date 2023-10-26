package io.github.astro.mantis.transport.netty.server;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.exception.BindException;
import io.github.astro.mantis.common.exception.NetWorkException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.channel.ChannelHandler;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.netty.NettyChannel;
import io.github.astro.mantis.transport.netty.codec.NettyCodec;
import io.github.astro.mantis.transport.server.AbstractServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public final class NettyServer extends AbstractServer {

    private ServerBootstrap bootstrap;
    private NioEventLoopGroup bossGroup;
    private NioEventLoopGroup workerGroup;
    private Channel channel;

    public NettyServer(URL url, ChannelHandler handler, Codec codec) throws BindException {
        super(url, handler, codec);
    }

    @Override
    protected void init() throws BindException {
        bootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("NettyServerBoss", true));
        workerGroup = new NioEventLoopGroup(Constant.DEFAULT_IO_THREADS, new DefaultThreadFactory("NettyServerWorker", true));
        final NettyServerChannelHandler handler = new NettyServerChannelHandler(channelHandler);
        initServerBootStrap(handler);
    }

    private void initServerBootStrap(NettyServerChannelHandler handler) {
        bootstrap.group(bossGroup, workerGroup)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
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
    protected void doBind() throws BindException {
        ChannelFuture future = bootstrap.bind(getPort());
        future.syncUninterruptibly();
        channel = future.channel();
    }

    @Override
    protected void doClose() throws NetWorkException {
        try {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
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
