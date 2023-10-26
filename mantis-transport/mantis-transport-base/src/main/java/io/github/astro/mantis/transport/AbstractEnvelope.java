package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.transport.header.Header;

import java.io.Serial;

public abstract class AbstractEnvelope implements Envelope {

    @Serial
    private static final long serialVersionUID = 1L;
    private Header header;
    private Object body;

    public AbstractEnvelope(Header header, Object body) {
        this.header = header;
        this.body = body;
    }

    public AbstractEnvelope() {
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setHeader(Header header) {
        this.header = header;
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }

    public String getId() {
        return header.getExtendData(Key.UNIQUE_ID);
    }

    @Override
    public String toString() {
        return getId();
    }
}
