package io.github.astro.mantis.transport.http;

import io.github.astro.mantis.common.exception.ConnectException;
import io.github.astro.mantis.common.exception.NetWorkException;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/6 16:40
 */
public class AbstractHttpClient implements HttpEndpointClient {

    @Override
    public void close() throws NetWorkException {

    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void connect() throws ConnectException {

    }

    @Override
    public void send(Object message) {


    }
}
