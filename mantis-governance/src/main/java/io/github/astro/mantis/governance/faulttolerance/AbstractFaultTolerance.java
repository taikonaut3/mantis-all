package io.github.astro.mantis.governance.faulttolerance;

import io.github.astro.mantis.common.exception.RpcException;
import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceProviderLoader;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.invoke.Result;
import io.github.astro.mantis.protocol.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFaultTolerance implements FaultTolerance {

    private static final Logger logger = LoggerFactory.getLogger(AbstractFaultTolerance.class);

    @Override
    public Result operation(URL url, Invocation invocation) {
        logger.debug(this.getClass().getSimpleName() + " start...");
        // todo 限流、熔断
        return doOperation(url, invocation);
    }

    // 容错机制
    protected abstract Result doOperation(URL url, Invocation invocation);

    protected Result requestInvoke(URL url, Invocation invocation) {
        Result result;
        try {
            Protocol protocol = ServiceProviderLoader.loadService(Protocol.class, url.getProtocol());
            result = protocol.send(url, invocation);
            result.getValue();
        } catch (Exception e) {
            logger.error("Request Invoke Error", e);
            throw new RpcException(e);
        }
        return result;
    }
}
