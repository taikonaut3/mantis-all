package io.github.astro.mantis.transport.codec;

import io.github.astro.mantis.common.exception.CodecException;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.codec.byteutils.ByteReader;
import io.github.astro.mantis.transport.codec.byteutils.ByteWriter;

public interface Codec {

    ByteWriter encode(Envelope envelope, ByteWriter writer) throws CodecException;

    Envelope decode(ByteReader reader) throws CodecException;

}