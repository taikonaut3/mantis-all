package io.github.astro.mantis.transport.codec;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.CodecException;
import io.github.astro.mantis.common.util.ReflectUtils;
import io.github.astro.mantis.serialization.Serializer;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.codec.byteutils.ByteWriter;
import io.github.astro.mantis.transport.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCodec implements Codec {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);
    protected final Class<? extends Envelope> encodeFrom;
    protected final Class<? extends Envelope> decodeTo;

    public AbstractCodec(Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo) {
        this.encodeFrom = encodeFrom;
        this.decodeTo = decodeTo;
    }

    @Override
    public ByteWriter encode(Envelope envelope, ByteWriter writer) throws CodecException {
        try {
            encodeHeader(envelope, writer);
            encodeBody(envelope, writer);
            return writer;
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " encode Error", e);
            throw new CodecException(e);
        }
    }

    @Override
    public Envelope decode(ByteReader reader) throws CodecException {
        try {
            Header header = decodeHeader(reader);
            Object body = decodeBody(header, reader);
            return ReflectUtils.createInstance(decodeTo, header, body);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " decode Error", e);
            throw new CodecException(e);
        }
    }

    protected void encodeHeader(Envelope envelope, ByteWriter writer) {
        Header header = envelope.getHeader();
        header.addExtendData(Key.BODY_TYPE, envelope.getBody().getClass().getName());
        // 将header转为二进制并且将header长度写在最前面
        writer.writeBytes(envelope.getHeader().toBytes());
    }

    protected void encodeBody(Envelope envelope, ByteWriter writer) {
        Serializer serializer = envelope.getHeader().getSerializer();
        byte[] bytes = serializer.serialize(envelope.getBody());
        logger.debug("{}: [serializer: {}]", this.getClass().getSimpleName(), serializer.getClass().getSimpleName());
        writer.writeBytes(bytes);
    }

    protected Object decodeBody(Header header, ByteReader reader) {
        Serializer serializer = header.getSerializer();
        Class<?> bodyType;
        try {
            bodyType = Class.forName(header.getExtendData(Key.BODY_TYPE));
        } catch (ClassNotFoundException e) {
            bodyType = Object.class;
        }
        byte[] bytes = reader.readBytes(reader.readableBytes());
        logger.debug("{}: [deserializer: {}]", this.getClass().getSimpleName(), serializer.getClass().getSimpleName());
        return serializer.deserialize(bytes, bodyType);

    }

    protected abstract Header decodeHeader(ByteReader reader);
}
