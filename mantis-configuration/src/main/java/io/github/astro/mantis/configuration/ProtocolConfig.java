package io.github.astro.mantis.configuration;

import io.github.astro.mantis.common.constant.Constant;
import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.ProtocolType;
import io.github.astro.mantis.common.util.NetUtils;
import io.github.astro.mantis.configuration.annotation.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class ProtocolConfig extends AbstractConfig {

    private ProtocolType type;

    @Parameter(Key.SSL)
    private boolean ssl = true;

    @Parameter(Key.MAX_MESSAGE_SIZE)
    private int maxMessageSize = Constant.DEFAULT_MAX_MESSAGE_SIZE;

    @Parameter(Key.CONNECT_TIMEOUT)
    private int connectTimeout = Constant.DEFAULT_CONNECT_TIMEOUT;

    @Parameter(Key.TIMEOUT)
    private int exchangeTimeout = Constant.DEFAULT_TIMEOUT;

    @Parameter(Key.COMPRESSION)
    private boolean compression = true;

    @Parameter(Key.KEEPALIVE)
    private boolean keepAlive = true;

    @Override
    public URL toUrl() {
        return new URL(type.getName(), NetUtils.getLocalHost(), port, parameterization());
    }
}
