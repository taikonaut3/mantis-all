package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.NetWorkException;

public interface Closeable {

    /**
     * Close Resource
     *
     * @throws NetWorkException
     */
    void close() throws NetWorkException;

    /**
     * Whether the Current Channel is Active (can read or write)
     * for processing when the channel is inactive
     *
     * @return
     */
    boolean isActive();

}
