package io.github.astro.mantis.configuration.base;

import io.github.astro.mantis.common.constant.Key;
import io.github.astro.mantis.common.constant.ProtocolType;
import io.github.astro.mantis.configuration.annotation.Parameter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public abstract class AbstractCallOptions extends AbstractOptions implements CallOptions {

    @Parameter(Key.RETRIES)
    protected int retires;
    @Parameter(Key.IS_ASYNC)
    private boolean async;
    @Parameter(Key.PROTOCOL)
    private String protocol;
    @Parameter(Key.LOAD_BALANCE)
    private String loadBalance;
    @Parameter(Key.DIRECTORY)
    private String directory;
    @Parameter(Key.ROUTER)
    private String router;
    @Parameter(Key.FAULT_TOLERANCE)
    private String faultTolerance;
    @Parameter(Key.TIMEOUT)
    private int timeout;
    @Parameter(Key.TRANSPORT)
    private String transport;

    @Override
    public ProtocolType getProtocol() {
        return ProtocolType.get(protocol);
    }
}
