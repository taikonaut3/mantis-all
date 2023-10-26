package io.github.astro.mantis.transport.codec.byteutils;

public interface ByteWriter {
    static ByteWriter defaultWriter() {
        return new SimpleByteWriter();
    }

    void writeByte(byte value);

    void writeBoolean(boolean value);

    void writeShort(short value);

    void writeInt(int value);

    void writeInt(int value, int index);

    void writeLong(long value);

    void writeFloat(float value);

    void writeDouble(double value);

    void writeBytes(byte[] bytes);

    void writeCharSequence(CharSequence value);

    int writableBytes();

    byte[] toBytes();

    default ByteWriter writeHeadInt(int value) {
        writeInt(value, 0);
        return this;
    }

    default ByteWriter writeLength() {
        return writeHeadInt(writableBytes());
    }

}