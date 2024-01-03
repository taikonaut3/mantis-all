package io.github.astro.mantis.protocol.mantis;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.Response;
import io.github.astro.mantis.byteutils.ByteReader;
import io.github.astro.mantis.byteutils.ByteWriter;
import io.github.astro.mantis.code.AbstractCodec;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.exception.CodecException;
import io.github.astro.mantis.protocol.mantis.envelope.MantisEnvelope;
import io.github.astro.mantis.protocol.mantis.header.Header;
import io.github.astro.mantis.protocol.mantis.header.MantisHeader;
import io.github.astro.mantis.serialization.Serializer;
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
public class MantisCodec extends AbstractCodec {

    private static final Logger logger = LoggerFactory.getLogger(MantisCodec.class);

    public MantisCodec(Class<? extends MantisEnvelope> encodedClass, Class<? extends MantisEnvelope> decodedClass) {
        super(encodedClass, decodedClass);
    }

    @Override
    protected byte[] encodeRequestMessage(Request request) {
        Object message = request.getMessage();
        if (message instanceof MantisEnvelope mantisEnvelope) {
            return encodeEnvelope(mantisEnvelope);
        } else {
            throw new IllegalArgumentException("The current encoder only supports requests for the mantis protocol");
        }
    }

    @Override
    protected Object decodeRequestMessage(Request request, byte[] messageBytes) {
        return decodeEnvelope(messageBytes);
    }

    @Override
    protected byte[] encodeResponseMessage(Response response) {
        Object message = response.getMessage();
        if (message instanceof MantisEnvelope mantisEnvelope) {
            return encodeEnvelope(mantisEnvelope);
        } else {
            throw new IllegalArgumentException("The current encoder only supports requests for the mantis protocol");
        }
    }

    @Override
    protected Object decodeResponseMessage(Response response, byte[] messageBytes) {
        return decodeEnvelope(messageBytes);
    }
    private byte[] encodeEnvelope(MantisEnvelope mantisEnvelope) throws CodecException {
        ByteWriter writer = ByteWriter.newWriter();
        try {
            byte[] fixHeaderBytes = encodeFixHeader(mantisEnvelope);
            byte[] extendHeaderBytes = encodeExtendHeader(mantisEnvelope);
            byte[] bodyBytes = encodeBody(mantisEnvelope);
            writer.writeInt(mantisEnvelope.getHeader().getLength());
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

    private MantisEnvelope decodeEnvelope(byte[] bytes) throws CodecException {
        ByteReader reader = ByteReader.newReader(bytes);
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

    private byte[] encodeFixHeader(MantisEnvelope mantisEnvelope) {
        return mantisEnvelope.getHeader().fixDataToBytes();
    }

    private byte[] encodeExtendHeader(MantisEnvelope mantisEnvelope) {
        Header header = mantisEnvelope.getHeader();
        header.addExtendData(Key.BODY_TYPE, mantisEnvelope.getBody().getClass().getName());
        return header.extendDataToBytes();
    }

    private byte[] encodeBody(MantisEnvelope mantisEnvelope) {
        Serializer serializer = mantisEnvelope.getHeader().getSerializer();
        byte[] bytes = serializer.serialize(mantisEnvelope.getBody());
        logger.debug("{}: [serializer: {}]", this.getClass().getSimpleName(), serializer.getClass().getSimpleName());
        return bytes;
    }

    @SuppressWarnings("unchecked")
    private Header decodeHeader(byte[] fixHeaderBytes, byte[] extendHeaderBytes) {
        Header header = decodeFixHeader(fixHeaderBytes);
        HashMap<String, String> extendData = (HashMap<String, String>) header.getSerializer().deserialize(extendHeaderBytes, HashMap.class);
        header.addExtendData(extendData);
        return header;
    }

    private Header decodeFixHeader(byte[] fixHeaderBytes) {
        return MantisHeader.parse(fixHeaderBytes);
    }

    private Object decodeBody(Header header, byte[] bodyBytes) {
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

    @SuppressWarnings("unchecked")
    private MantisEnvelope createEnvelope(Header header, Object body) {
        Constructor<? extends MantisEnvelope> constructor;
        MantisEnvelope mantisEnvelope;
        try {
            constructor = (Constructor<? extends MantisEnvelope>) decodedClass.getConstructor(header.getClass(), Object.class);
            mantisEnvelope = constructor.newInstance(header, body);
        } catch (NoSuchMethodException e) {
            try {
                constructor = (Constructor<? extends MantisEnvelope>) decodedClass.getConstructor();
                mantisEnvelope = constructor.newInstance();
                mantisEnvelope.setHeader(header);
                mantisEnvelope.setBody(body);
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
        return mantisEnvelope;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[encode->" + encodedClass.getSimpleName() + ", decode->" + decodedClass.getSimpleName() + "]";
    }

}
