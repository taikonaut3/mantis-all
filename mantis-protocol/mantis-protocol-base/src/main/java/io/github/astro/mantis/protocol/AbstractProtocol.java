package io.github.astro.mantis.protocol;

import io.github.astro.mantis.code.Codec;
import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.configuration.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractProtocol implements Protocol {

    private static final Logger logger = LoggerFactory.getLogger(AbstractProtocol.class);

    protected Mode protocolMode;

    protected volatile Codec serverCodec;

    protected volatile Codec clientCodec;

    protected volatile ProtocolParser protocolParser;

    public AbstractProtocol(Mode protocolMode) {
        this.protocolMode = protocolMode;
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

    @Override
    public ProtocolParser getParser(URL url) {
        if (protocolParser == null) {
            synchronized (this) {
                if (protocolParser == null) {
                    protocolParser = createProtocolParser(url);
                    logger.debug("Created protocolParser: {}", protocolParser);
                }
            }
        }
        return protocolParser;
    }

    protected abstract Codec createServerCodec(URL url);

    protected abstract Codec createClientCodec(URL url);

    protected abstract ProtocolParser createProtocolParser(URL url);

    @Override
    public Mode getProtocolMode() {
        return protocolMode;
    }

}
