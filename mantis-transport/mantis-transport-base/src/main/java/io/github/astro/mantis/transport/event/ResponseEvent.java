package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.Response;
import io.github.astro.mantis.event.AbstractEvent;
import io.github.astro.mantis.util.ProtocolUtil;

public class ResponseEvent extends AbstractEvent<Response> {

    private Object body;

    public ResponseEvent(Response response) {
        super(response);
        this.body = ProtocolUtil.parseBody(response);
    }

    public Object getBody() {
        return body;
    }

}
