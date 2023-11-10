package io.github.astro.mantis.transport.netty.codec;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.codec.Codec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import static io.github.astro.mantis.common.constant.Constant.DEFAULT_MAX_MESSAGE_SIZE;

/**
 * Read the Envelope by data length
 */
public final class NettyCodec {

    private final ChannelHandler encoder;

    private final ChannelHandler decoder;

    private final Codec codec;

    public NettyCodec(URL url, Codec codec) {
        this.codec = codec;
        int maxReceiveSize;
        if (codec.getEncodedClass() == Request.class) {
            maxReceiveSize = url.getIntParameter(Key.CLIENT_MAX_RECEIVE_SIZE, DEFAULT_MAX_MESSAGE_SIZE);
        } else {
            maxReceiveSize = url.getIntParameter(Key.SERVER_MAX_RECEIVE_SIZE, DEFAULT_MAX_MESSAGE_SIZE);
        }
        encoder = new NettyEncoder();
        decoder = new NettyDecoder(maxReceiveSize);
    }

    public ChannelHandler getEncoder() {
        return encoder;
    }

    public ChannelHandler getDecoder() {
        return decoder;
    }

    class NettyEncoder extends MessageToByteEncoder<Envelope> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Envelope envelope, ByteBuf out) throws Exception {
            byte[] bytes = codec.encode(envelope);
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }

    }

    class NettyDecoder extends LengthFieldBasedFrameDecoder {

        public NettyDecoder(int maxFrameLength) {
            super(maxFrameLength, 0, 4, 0, 4, true);
        }

        @Override
        protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
            ByteBuf byteBuf = (ByteBuf) super.decode(ctx, in);
            if (byteBuf != null) {
                byte[] bytes = new byte[byteBuf.readableBytes()];
                byteBuf.readBytes(bytes);
                return codec.decode(bytes);
            }
            return null;
        }

    }

}
