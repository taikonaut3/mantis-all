package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.transport.Response;

public class ResponseEvent extends EnvelopeEvent<Response> {

    public ResponseEvent(Response response) {
        super(response);
    }

}
