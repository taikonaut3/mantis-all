package io.github.astro.mantis.protocol;

import io.github.astro.mantis.common.constant.Mode;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.spi.ServiceInterface;
import io.github.astro.mantis.transport.Request;
import io.github.astro.mantis.transport.Response;
import io.github.astro.mantis.transport.codec.Codec;

import static io.github.astro.mantis.common.constant.KeyValues.Protocol.MANTIS;

/**
 * Communication protocol used to exchange data between client and server.
 */
@ServiceInterface(MANTIS)
public interface Protocol {

    /**
     * The mode of this protocol.
     *
     * @return {@link Mode}
     */
    Mode getProtocolMode();

    /**
     * Creates a new request for the given URL and message body.
     *
     * @param url  the URL to which the request is sent
     * @param body the message body of the request
     * @return {@link Request}
     */
    Request createRequest(URL url, Object body);

    /**
     * Creates a new response for the given URL and message body.
     *
     * @param url  {@link URL}
     * @param body the message body of the response
     * @return {@link Response}
     */
    Response createResponse(URL url, Object body);

    /**
     * Gets Codec used by the server to encode and decode messages for the given URL.
     *
     * @param url {@link URL}
     * @return the codec used by the server
     */
    Codec getServerCodec(URL url);

    /**
     * Gets codec used by the client to encode and decode messages for the given URL.
     *
     * @param url {@link URL}
     * @return the codec used by the client
     */
    Codec getClientCodec(URL url);

}
