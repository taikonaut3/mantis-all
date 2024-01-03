package io.github.astro.mantis;

import io.github.astro.mantis.configuration.URL;
import lombok.Data;

@Data
public class Response implements Envelope {

    public static final byte SUCCESS = 0, ERROR = -1, TIMEOUT = 3;

    private byte code;

    private Long id;

    private URL url;

    private Object message;

    public Response() {
    }

    public Response(URL url, Object message) {
        this.url = url;
        this.message = message;
    }

}
