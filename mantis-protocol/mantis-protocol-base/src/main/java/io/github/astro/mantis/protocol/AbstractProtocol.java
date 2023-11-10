package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.codec.Codec;
import io.github.astro.mantis.transport.header.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProtocol implements Protocol {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProtocol.class);

    protected Mode protocolMode;

    protected volatile Codec serverCodec;

    protected volatile Codec clientCodec;

    public AbstractProtocol(Mode protocolMode) {
        this.protocolMode = protocolMode;
    }

    @Override
    public Request createRequest(URL url, Object body) {
        Header header = createRequestHeader(url);
        return new Request(header, body);
    }

    @Override
    public Response createResponse(URL url, Object body) {
        Header header = createReponseHeader(url);
        return new Response(header, body);
    }

    @Override
    public Codec getServerCodec(URL url) {
        if (serverCodec == null) {
            synchronized (this) {
                if (serverCodec == null) {
                    serverCodec = createServerCodec(url);
                    logger.debug("Created ServerCodec: {}", serverCodec);
                }
            }
        }
        return serverCodec;
    }

    @Override
    public Codec getClientCodec(URL url) {
        if (clientCodec == null) {
            synchronized (this) {
                if (clientCodec == null) {
                    clientCodec = createClientCodec(url);
                    logger.debug("Created ClientCodec: {}", clientCodec);
                }
            }
        }
        return clientCodec;
    }

    protected abstract Header createRequestHeader(URL url);

    protected abstract Header createReponseHeader(URL url);

    protected abstract Codec createServerCodec(URL url);

    protected abstract Codec createClientCodec(URL url);

    @Override
    public Mode getProtocolMode() {
        return protocolMode;
    }

}
