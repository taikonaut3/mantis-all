package io.github.astro.mantis.protocol;

import io.github.astro.mantis.configuration.URL;

import java.io.Serializable;

public interface Envelope extends Serializable {

    URL getUrl();

    Object getPayload();

}
