package io.github.astro.mantis;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.configuration.URL;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

@Data
public class Request implements Envelope {

    private static final AtomicLong INCREASE = new AtomicLong(0);

    private Long id;

    private boolean oneway;

    private URL url;

    private Object message;

    public Request() {
    }

    public Request(URL url, Object message) {
        id = INCREASE.getAndIncrement();
        url.addParameter(Key.UNIQUE_ID, String.valueOf(id));
        this.url = url;
        this.message = message;

    }

}
