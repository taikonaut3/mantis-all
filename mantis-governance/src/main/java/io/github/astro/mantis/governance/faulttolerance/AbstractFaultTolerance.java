package io.github.astro.mantis.governance.faulttolerance;

import io.github.astro.mantis.configuration.CallData;
import io.github.astro.mantis.configuration.DirectRemoteCall;
import io.github.astro.mantis.configuration.Result;
import io.github.astro.mantis.configuration.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFaultTolerance implements FaultTolerance {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFaultTolerance.class);

    @Override
    public Result operation(URL url, CallData callData, DirectRemoteCall directRemoteCall) {
        logger.debug(this.getClass().getSimpleName() + " start...");
        // todo 限流、熔断
        return doOperation(url, callData, directRemoteCall);
    }

    // 容错机制
    protected abstract Result doOperation(URL url, CallData callData, DirectRemoteCall directRemoteCall);

}
