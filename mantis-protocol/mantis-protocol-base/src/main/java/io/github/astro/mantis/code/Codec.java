package io.github.astro.mantis.code;

import io.github.astro.mantis.common.exception.CodecException;

/**
 * Encoding and Decoding network message.
 */
public interface Codec {

    /**
     * Encodes the message into a byte array.
     *
     * @param message The message to be encoded.
     * @return The encoded byte array.
     * @throws CodecException
     */
    byte[] encode(Object message) throws CodecException;

    /**
     * Decodes the  byte array into a message.
     *
     * @param bytes The byte array to be decoded.
     * @return The decoded message.
     * @throws CodecException
     */
    Object decode(byte[] bytes) throws CodecException;

}