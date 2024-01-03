package io.github.astro.mantis.transport.client;

import io.github.astro.mantis.common.exception.ConnectException;
import io.github.astro.mantis.transport.Closeable;

/**
 * @Author WenBo Zhou
 * @Date 2023/12/6 16:48
 */
public interface Client extends Closeable {


    void connect() throws ConnectException;

    void send(Object message);
}
