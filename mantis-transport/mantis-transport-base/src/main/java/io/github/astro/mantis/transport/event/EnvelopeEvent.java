package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.event.AbstractEvent;
import io.github.astro.mantis.transport.Envelope;

public abstract class EnvelopeEvent<T extends Envelope> extends AbstractEvent<T> {

    private final URL url;

    protected EnvelopeEvent(T envelope) {
        super(envelope);
        this.url = URL.valueOf(envelope.getHeader().getExtendData(Key.URL));

    }

    public URL getUrl() {
        return url;
    }

    public String getId() {
        return getSource().getHeader().getExtendData(Key.UNIQUE_ID);
    }

}
