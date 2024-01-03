package io.github.astro.mantis.protocol.mantis.envelope;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.protocol.mantis.header.Header;

import java.io.Serializable;

/**
 * NetWork Transmission carrier
 * Comprise:
 * 1、Header Data {@link Header}
 * 2、Body Data
 */
public interface MantisEnvelope extends Serializable {

    /**
     * Returns the header of this message.
     *
     * @return the header of the message
     */
    Header getHeader();

    /**
     * Sets the header of this message.
     *
     * @param header the header to be set for the message
     */
    void setHeader(Header header);

    /**
     * Returns the body of this message.
     *
     * @return the body of the message
     */
    Object getBody();

    /**
     * Sets the body of this message.
     *
     * @param body the body to be set for the message
     */
    void setBody(Object body);

    /**
     * This URL may not be what you would expect
     *
     * @return
     */
    URL getUrl();

}
