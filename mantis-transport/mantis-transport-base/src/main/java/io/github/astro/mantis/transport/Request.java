package io.github.astro.mantis.transport;

import io.github.astro.mantis.transport.header.Header;

public class Request extends AbstractEnvelope {

    public Request(Header header, Object body) {
        super(header, body);
    }

    public Request() {
        super();
    }
}
