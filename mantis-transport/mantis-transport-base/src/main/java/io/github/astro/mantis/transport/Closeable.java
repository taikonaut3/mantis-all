package io.github.astro.mantis.transport;

import io.github.astro.mantis.common.exception.NetWorkException;

public interface Closeable {

    void close() throws NetWorkException;

    /**
     * 判断当前通道是否处于活动状态（能否读写），以便在通道不活跃时进行处理
     *
     * @return
     */
    boolean isActive();

}
