package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.common.constant.ModeContainer;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.codec.byteutils.ByteWriter;
import io.github.astro.mantis.transport.header.AbstractHeader;

public class MantisHeader extends AbstractHeader {

    private static final int magic = 888;


    protected MantisHeader(Mode serializeMode, Mode envelopeMode, Mode protocolMode) {
        super(envelopeMode, protocolMode);
        setSerializeMode(serializeMode);
    }

    @SuppressWarnings("unchecked")
    public static MantisHeader parse(byte[] bytes) {
        ByteReader reader = ByteReader.defaultReader(bytes);
        // 魔数位
        int magic = reader.readInt();
        if (MantisHeader.magic != magic) {
            throw new IllegalArgumentException("parse ProtocolHeader error");
        }
        // 协议版本
        Mode protocolMode = ModeContainer.getMode(Key.PROTOCOL, reader.readByte());
        // 消息类型
        Mode envelopeMode = ModeContainer.getMode(Key.ENVELOPE, reader.readByte());
        // 序列化方式
        Mode serializeMode = ModeContainer.getMode(Key.SERIALIZE, reader.readByte());
        return new MantisHeader(serializeMode, envelopeMode, protocolMode);
    }

    public Mode getSerializeMode() {
        String serializeName = getExtendData(Key.SERIALIZE);
        return ModeContainer.getMode(Key.SERIALIZE,serializeName);
    }

    public void setSerializeMode(Mode serializeMode) {
        addExtendData(Key.SERIALIZE, serializeMode.name());
    }

    @Override
    public byte[] fixDataToBytes() {
        ByteWriter writer = ByteWriter.defaultWriter();
        // 魔数位
        writer.writeInt(magic);
        // 协议版本
        writer.writeByte(getProtocolMode().type());
        // 消息类型
        writer.writeByte(getEnvelopeMode().type());
        // 序列化方式
        writer.writeByte(getSerializeMode().type());
        return writer.toBytes();
    }

}
