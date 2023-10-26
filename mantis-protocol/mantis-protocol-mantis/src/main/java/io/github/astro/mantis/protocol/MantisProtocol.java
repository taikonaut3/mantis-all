package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.*;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProvider;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.codec.AbstractCodec;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.header.Header;

import static io.github.astro.mantis.common.constant.ServiceType.Protocol.MANTIS;

@ServiceProvider(MANTIS)
public final class MantisProtocol extends AbstractProtocol {

    public MantisProtocol() {
        super(ProtocolType.MANTIS);
    }

    @Override
    public Codec createCodec(URL url, Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo) {
        return new MantisCodec(encodeFrom, decodeTo);
    }

    @Override
    public Header createHeader(URL url) {
        String serialize = url.getParameter(Key.SERIALIZE, Constant.DEFAULT_SERIALIZE);
        SerializerType serializeType = SerializerType.get(serialize);
        MessageType messageType = MessageType.get(Byte.parseByte(url.getParameter(Key.MESSAGE_TYPE)));
        MantisHeader header = new MantisHeader(serializeType, messageType, getType().getVersion());
        header.addExtendData(Key.URL, url.toString());
        return header;
    }

    public static final class MantisCodec extends AbstractCodec {

        public MantisCodec(Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo) {
            super(encodeFrom, decodeTo);

        }

        @Override
        protected Header decodeHeader(ByteReader reader) {
            return MantisHeader.parse(reader);
        }

    }
}
