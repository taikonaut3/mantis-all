package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.MessageType;
import io.github.astro.mantis.common.constant.SerializerType;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.serialization.Serializer;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.codec.byteutils.ByteWriter;
import io.github.astro.mantis.transport.header.AbstractHeader;

import java.util.HashMap;

public class MantisHeader extends AbstractHeader {

    private static final int magic = 888;

    protected MantisHeader(SerializerType serializerType, MessageType type, String protocolVersion) {
        super(type, protocolVersion);
        setSerializationType(serializerType);
    }

    protected MantisHeader(MessageType type, String protocolVersion) {
        super(type, protocolVersion);
    }

    @SuppressWarnings("unchecked")
    public static MantisHeader parse(ByteReader reader) {
        int headerLength = reader.readInt();
        int fixHeaderLength = reader.readInt();
        // 魔数位
        int magic = reader.readInt();
        if (MantisHeader.magic != magic) {
            throw new IllegalArgumentException("parse ProtocolHeader error");
        }
        // 协议版本
        String protocolVersion = reader.readCharSequence(3).toString();
        // 消息类型
        MessageType messageType = MessageType.get(reader.readByte());
        // 序列化方式
        SerializerType type = SerializerType.get(reader.readByte());
        assert type != null;
        Serializer serializer = ServiceProviderLoader.loadService(Serializer.class, type.getName());
        HashMap<String, String> extendData = (HashMap<String, String>) serializer.deserialize(reader.readBytes(headerLength - fixHeaderLength), HashMap.class);
        MantisHeader header = new MantisHeader(type, messageType, protocolVersion);
        header.addExtendData(extendData);
        if (header.getLength() != headerLength) {
            throw new IllegalArgumentException("parse ProtocolHeader error");
        }
        return header;
    }

    public SerializerType getSerializationType() {
        return SerializerType.get(getExtendData(Key.SERIALIZE));
    }

    public void setSerializationType(SerializerType serializerType) {
        addExtendData(Key.SERIALIZE, serializerType.getName());
        this.addExtendData(Key.SERIALIZE, serializerType.getName());
    }

    @Override
    public byte[] toBytes() {
        ByteWriter writer = ByteWriter.defaultWriter();
        // 魔数位
        writer.writeInt(magic);
        // 协议版本
        writer.writeCharSequence(getProtocolVersion());
        // 消息类型
        writer.writeByte(getMessageType().getType());
        // 序列化方式
        writer.writeByte(getSerializationType().getType());
        int fixLength = writer.writableBytes();
        writer.writeBytes(getSerializer().serialize(getExtendsData()));
        int length = writer.writableBytes();
        // 固定头长度
        writer.writeHeadInt(fixLength);
        // 头总长度
        writer.writeHeadInt(length);
        return writer.toBytes();
    }
}
