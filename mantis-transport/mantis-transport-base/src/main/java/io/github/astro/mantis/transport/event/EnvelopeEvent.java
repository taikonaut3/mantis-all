package io.github.astro.mantis.transport.event;

import io.github.astro.mantis.Envelope;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.event.AbstractEvent;

public abstract class EnvelopeEvent<T extends Envelope> extends AbstractEvent<T> {

    private final URL url;

    protected EnvelopeEvent(T envelope) {
        super(envelope);
        this.url = envelope.getUrl();

    }

    public URL getUrl() {
        return url;
    }

    public String getId() {
        return String.valueOf(getSource().getId());
    }

}
