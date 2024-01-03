package io.github.astro.mantis.protocol.mantis;

import io.github.astro.mantis.Request;
import io.github.astro.mantis.Response;
import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.constant.*;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceProvider;
import io.github.astro.mantis.protocol.AbstractProtocol;
import io.github.astro.mantis.protocol.ProtocolParser;
import io.github.astro.mantis.protocol.mantis.envelope.MantisRequest;
import io.github.astro.mantis.protocol.mantis.envelope.MantisResponse;
import io.github.astro.mantis.protocol.mantis.header.Header;
import io.github.astro.mantis.protocol.mantis.header.MantisHeader;

import static io.github.astro.mantis.common.constant.KeyValues.Protocol.MANTIS;

@ServiceProvider(MANTIS)
public final class MantisProtocol extends AbstractProtocol {

    public MantisProtocol() {
        super(ModeContainer.getMode(Key.PROTOCOL, MANTIS));
    }

    @Override
    protected Codec createServerCodec(URL url) {
        return new MantisCodec(MantisResponse.class, MantisRequest.class);
    }

    @Override
    protected Codec createClientCodec(URL url) {
        return new MantisCodec(MantisRequest.class, MantisResponse.class);
    }

    @Override
    public Request createRequest(URL url, Object payload) {
        url.addParameter(Key.ENVELOPE,KeyValues.Envelope.REQUEST);
        MantisRequest mantisRequest = new MantisRequest(createHeader(url, KeyValues.Envelope.REQUEST), payload);
        return new Request(url, mantisRequest);
    }

    @Override
    public Response createResponse(URL url, Object payload) {
        url.addParameter(Key.ENVELOPE,KeyValues.Envelope.RESPONSE);
        MantisResponse mantisResponse = new MantisResponse(createHeader(url, KeyValues.Envelope.RESPONSE), payload);
        return new Response(url, mantisResponse);
    }

    @Override
    protected ProtocolParser createProtocolParser(URL url) {
        return new MantisProtocolParser();
    }

    private Header createHeader(URL url, String envelope) {
        String serialize = url.getParameter(Key.SERIALIZE, Constant.DEFAULT_SERIALIZE);
        Mode serializeMode = ModeContainer.getMode(Key.SERIALIZE, serialize);
        Mode responseMode = ModeContainer.getMode(Key.ENVELOPE, envelope);
        MantisHeader header = new MantisHeader(serializeMode, responseMode, getProtocolMode());
        header.addExtendData(Key.URL, url.toString());
        return header;
    }

}
