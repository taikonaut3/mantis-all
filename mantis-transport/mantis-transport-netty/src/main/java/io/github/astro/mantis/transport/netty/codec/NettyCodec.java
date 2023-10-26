package io.github.astro.mantis.transport.netty.codec;

import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.codec.byteutils.ByteWriter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

public final class NettyCodec {

    private final ChannelHandler encoder = new NettyEncoder();

    private final ChannelHandler decoder = new NettyDecoder();

    private final Codec codec;

    public NettyCodec(Codec codec) {
        this.codec = codec;
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    class NettyEncoder extends MessageToByteEncoder<Object> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
            // NettyChannel channel = NettyChannel.getChannel(ctx.channel());
            if (msg instanceof Envelope envelope) {
                ByteWriter writer = ByteWriter.defaultWriter();
                byte[] bytes = codec.encode(envelope, writer).writeLength().toBytes();
                out.writeBytes(bytes);
            }
        }

    }

    class NettyDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            //NettyChannel channel = NettyChannel.getChannel(ctx.channel());
            // 判断可读字节数是否大于等于4
            while (in.readableBytes() >= 4) {
                // 标记当前读取的位置，防止读取不完整的消息
                in.markReaderIndex();
                // --- 读取总消息长度 ---
                int length = in.readInt();
                // 如果剩余可读字节数小于消息长度，则说明消息不完整，重置读取位置
                if (in.readableBytes() < length) {
                    in.resetReaderIndex();
                    return;
                }
                byte[] bytes = new byte[length];
                // 读取指定长度的字节内容
                in.readBytes(bytes);
                ByteReader reader = ByteReader.defaultReader(bytes);
                out.add(codec.decode(reader));
            }
        }

    }
}
