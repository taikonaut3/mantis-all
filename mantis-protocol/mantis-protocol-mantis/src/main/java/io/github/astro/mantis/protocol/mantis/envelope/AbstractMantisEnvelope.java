package io.github.astro.mantis.protocol.mantis.envelope;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.protocol.mantis.header.Header;

import java.io.Serial;

public abstract class AbstractMantisEnvelope implements MantisEnvelope {

    @Serial
    private static final long serialVersionUID = 1L;

    private Header header;

    private Object body;

    private URL url;

    public AbstractMantisEnvelope() {
    }

    public AbstractMantisEnvelope(Header header, Object body) {
        setHeader(header);
        setBody(body);
    }

    @Override
    public Header getHeader() {
        return header;
    }

    @Override
    public void setHeader(Header header) {
        this.header = header;
        url = URL.valueOf(header.getExtendData(Key.URL));
    }

    @Override
    public Object getBody() {
        return body;
    }

    @Override
    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public URL getUrl() {
        return url;
    }

    public String getId() {
        return header.getExtendData(Key.UNIQUE_ID);
    }

    public void setId(String id) {
        header.addExtendData(Key.UNIQUE_ID, String.valueOf(id));
    }

    @Override
    public String toString() {
        return getId();
    }

}
