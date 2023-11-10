package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.common.constant.ModeContainer;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.transport.Envelope;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.codec.AbstractCodec;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.header.Header;

import static io.github.astro.mantis.common.constant.KeyValues.Envelope.REQUEST;
import static io.github.astro.mantis.common.constant.KeyValues.Envelope.RESPONSE;
import static io.github.astro.mantis.common.constant.KeyValues.Protocol.MANTIS;

@ServiceProvider(MANTIS)
public final class MantisProtocol extends AbstractProtocol {

    public MantisProtocol() {
        super(ModeContainer.getMode(Key.PROTOCOL, MANTIS));
    }

    @Override
    protected Header createRequestHeader(URL url) {
        return createHeader(url, REQUEST);
    }

    @Override
    protected Header createReponseHeader(URL url) {
        return createHeader(url, RESPONSE);
    }

    private Header createHeader(URL url, String envelope) {
        String serialize = url.getParameter(Key.SERIALIZE, Constant.DEFAULT_SERIALIZE);
        Mode serializeMode = ModeContainer.getMode(Key.SERIALIZE, serialize);
        Mode responseMode = ModeContainer.getMode(Key.ENVELOPE, envelope);
        MantisHeader header = new MantisHeader(serializeMode, responseMode, getProtocolMode());
        header.addExtendData(Key.URL, url.toString());
        return header;
    }

    @Override
    protected Codec createServerCodec(URL url) {
        return new MantisCodec(Response.class, Request.class);
    }

    @Override
    protected Codec createClientCodec(URL url) {
        return new MantisCodec(Request.class, Response.class);
    }

    public static final class MantisCodec extends AbstractCodec {

        public MantisCodec(Class<? extends Envelope> encodeFrom, Class<? extends Envelope> decodeTo) {
            super(encodeFrom, decodeTo);
        }

        @Override
        protected Header decodeFixHeader(byte[] fixHeaderBytes) {
            return MantisHeader.parse(fixHeaderBytes);
        }

    }

}
