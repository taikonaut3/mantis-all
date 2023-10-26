package io.github.astro.mantis.transport;

import io.github.astro.mantis.transport.header.Header;

import java.io.Serializable;

public interface Envelope extends Serializable {

    Header getHeader();

    void setHeader(Header header);

    Object getBody();

    void setBody(Object body);

}
