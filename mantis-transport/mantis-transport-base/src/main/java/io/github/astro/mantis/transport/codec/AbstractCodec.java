package io.github.astro.mantis.transport.codec;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.CodecException;
import io.github.astro.mantis.serialization.Serializer;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.codec.byteutils.ByteWriter;
import io.github.astro.mantis.transport.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.HashMap;

/**
 * Envelope is formed in the following Order:
 * 1、Total length of Envelope: 4 bytes
 * 2、Total length of Header: 4 bytes
 * 3、Fix length of Header: 4 bytes
 * 4、Header Data
 * 5、Body Data
 * <p>
 * +-----------+---------- +--------------+-------------+------------+
 * | TotalLen  | HeaderLen | FixHeaderLen | Header Data | Body Data  |
 * | (4 bytes) | (4 bytes) |  (4 bytes)   | (variable)  | (variable) |
 * +-----------+----------+---------------+-------------+------------+
 * <p>
 * Especial: Total length Reflected in the network framework (eg: Netty)
 *
 * @see
 */
public abstract class AbstractCodec implements Codec {

    private static final Logger logger = LoggerFactory.getLogger(AbstractCodec.class);

    protected final Class<? extends Envelope> encodedClass;

    protected final Class<? extends Envelope> decodedClass;

    public AbstractCodec(Class<? extends Envelope> encodedClass, Class<? extends Envelope> decodedClass) {
        this.encodedClass = encodedClass;
        this.decodedClass = decodedClass;
    }

    @Override
    public byte[] encode(Envelope envelope) throws CodecException {
        ByteWriter writer = ByteWriter.defaultWriter();
        try {
            byte[] fixHeaderBytes = encodeFixHeader(envelope);
            byte[] extendHeaderBytes = encodeExtendHeader(envelope);
            byte[] bodyBytes = encodeBody(envelope);
            writer.writeInt(envelope.getHeader().getLength());
            writer.writeInt(fixHeaderBytes.length);
            writer.writeBytes(fixHeaderBytes);
            writer.writeBytes(extendHeaderBytes);
            writer.writeBytes(bodyBytes);
            return writer.toBytes();
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " encode Error", e);
            throw new CodecException(e);
        }
    }

    @Override
    public Envelope decode(byte[] bytes) throws CodecException {
        ByteReader reader = ByteReader.defaultReader(bytes);
        try {
            int totalHeaderLength = reader.readInt();
            int fixHeaderLength = reader.readInt();
            byte[] fixHeaderBytes = reader.readBytes(fixHeaderLength);
            byte[] extendHeaderBytes = reader.readBytes(totalHeaderLength - fixHeaderLength);
            byte[] bodyBytes = reader.readBytes(reader.readableBytes());
            Header header = decodeHeader(fixHeaderBytes, extendHeaderBytes);
            Object body = decodeBody(header, bodyBytes);
            return createEnvelope(header, body);
        } catch (Exception e) {
            logger.error(this.getClass().getSimpleName() + " decode Error", e);
            throw new CodecException(e);
        }
    }

    protected byte[] encodeFixHeader(Envelope envelope) {
        return envelope.getHeader().fixDataToBytes();
    }

    protected byte[] encodeExtendHeader(Envelope envelope) {
        Header header = envelope.getHeader();
        header.addExtendData(Key.BODY_TYPE, envelope.getBody().getClass().getName());
        return header.extendDataToBytes();
    }

    protected byte[] encodeBody(Envelope envelope) {
        Serializer serializer = envelope.getHeader().getSerializer();
        byte[] bytes = serializer.serialize(envelope.getBody());
        logger.debug("{}: [serializer: {}]", this.getClass().getSimpleName(), serializer.getClass().getSimpleName());
        return bytes;
    }

    protected Object decodeBody(Header header, byte[] bodyBytes) {
        Serializer serializer = header.getSerializer();
        Class<?> bodyType;
        try {
            bodyType = Class.forName(header.getExtendData(Key.BODY_TYPE));
        } catch (ClassNotFoundException e) {
            bodyType = Object.class;
        }
        logger.debug("{}: [deserializer: {}]", this.getClass().getSimpleName(), serializer.getClass().getSimpleName());
        return serializer.deserialize(bodyBytes, bodyType);

    }

    @Override
    public Class<? extends Envelope> getEncodedClass() {
        return encodedClass;
    }

    @Override
    public Class<? extends Envelope> getDecodedClass() {
        return decodedClass;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[encode->" + encodedClass.getSimpleName() + ", decode->" + decodedClass.getSimpleName() + "]";
    }

    @SuppressWarnings("unchecked")
    protected Header decodeHeader(byte[] fixHeaderBytes, byte[] extendHeaderBytes) {
        Header header = decodeFixHeader(fixHeaderBytes);
        HashMap<String, String> extendData = (HashMap<String, String>) header.getSerializer().deserialize(extendHeaderBytes, HashMap.class);
        header.addExtendData(extendData);
        return header;
    }

    protected abstract Header decodeFixHeader(byte[] fixHeaderBytes);

    private Envelope createEnvelope(Header header, Object body) {
        Constructor<? extends Envelope> constructor;
        Envelope envelope;
        try {
            constructor = decodedClass.getConstructor(header.getClass(), Object.class);
            envelope = constructor.newInstance(header, body);
        } catch (NoSuchMethodException e) {
            try {
                constructor = decodedClass.getConstructor();
                envelope = constructor.newInstance();
                envelope.setHeader(header);
                envelope.setBody(body);
            } catch (NoSuchMethodException ex) {
                logger.error("Can't find available Constructor,Need Envelope() or Envelope(Header,Object)", e);
                throw new CodecException(ex);
            } catch (Exception cause) {
                logger.error("Create Envelope Error by Envelope(Header,Object) constructor", e);
                throw new CodecException(cause);
            }
        } catch (Exception e) {
            logger.error("Create Envelope Error by No-argument constructor", e);
            throw new CodecException(e);
        }
        return envelope;
    }

}
