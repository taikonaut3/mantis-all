package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.transport.header.Header;

import java.util.concurrent.atomic.AtomicLong;

public class Request extends AbstractEnvelope {

    private static final AtomicLong INCREASE = new AtomicLong(0);

    public Request() {
    }

    public Request(Header header, Object body) {
        super(header, body);
        getHeader().addExtendData(Key.UNIQUE_ID, String.valueOf(INCREASE.getAndIncrement()));

    }

    public boolean isOneway() {
        return Boolean.parseBoolean(getHeader().getExtendData(Key.IS_ONEWAY));
    }

    public void oneway(boolean isOneway) {
        getHeader().addExtendData(Key.IS_ONEWAY, String.valueOf(isOneway));
    }

}
