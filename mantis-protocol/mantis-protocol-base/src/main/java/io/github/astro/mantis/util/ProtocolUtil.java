package io.github.astro.mantis.util;

import io.github.astro.mantis.Envelope;
import io.github.astro.mantis.Request;
import io.github.astro.mantis.Response;
import io.github.astro.mantis.configuration.spi.ExtensionLoader;
import io.github.astro.mantis.protocol.Protocol;
import io.github.astro.mantis.protocol.ProtocolParser;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/4 16:18
 */
public class ProtocolUtil {

    public static Object parseBody(Envelope envelope) {
        String protocolKey = envelope.getUrl().getProtocol();
        Protocol protocol = ExtensionLoader.loadService(Protocol.class, protocolKey);
        ProtocolParser protocolParser = protocol.getParser(envelope.getUrl());
        if (envelope instanceof Request request) {
            return protocolParser.parseBody(request);
        } else {
            Response response = (Response) envelope;
            return protocolParser.parseBody(response);
        }
    }
}
