package io.github.astro.mantis.transport.codec.byteutils;

/**
 * Tool for Reading bytes
 */
public interface ByteReader {

    static ByteReader defaultReader(byte[] bytes) {
        return new DefaultByteReader(bytes);
    }

    byte readByte();

    boolean readBoolean();

    short readShort();

    int readInt();

    long readLong();

    float readFloat();

    double readDouble();

    CharSequence readCharSequence(int length);

    byte[] readBytes(int length);

    int readableBytes();

}