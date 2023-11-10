package io.github.astro.mantis.transport.codec;

import io.github.astro.mantis.common.exception.CodecException;
import io.github.astro.mantis.transport.Envelope;

/**
 * Encoding and Decoding network envelope.
 */
public interface Codec {

    /**
     * Encodes the  envelope into a byte array.
     *
     * @param envelope The envelope to be encoded.
     * @return The encoded byte array.
     * @throws CodecException
     */
    byte[] encode(Envelope envelope) throws CodecException;

    /**
     * Decodes the  byte array into an envelope.
     *
     * @param bytes The byte array to be decoded.
     * @return The decoded envelope.
     * @throws CodecException
     */
    Envelope decode(byte[] bytes) throws CodecException;

    /**
     * Gets Encoded Class
     *
     * @return
     */
    Class<? extends Envelope> getEncodedClass();

    /**
     * Gets Decoded Class
     *
     * @return
     */
    Class<? extends Envelope> getDecodedClass();

}