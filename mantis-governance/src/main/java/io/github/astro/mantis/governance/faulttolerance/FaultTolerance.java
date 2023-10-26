package io.github.astro.mantis.governance.faulttolerance;

import io.github.astro.mantis.configuration.URL;
import io.github.astro.mantis.configuration.extension.spi.ServiceInterface;
import io.github.astro.mantis.configuration.invoke.Invocation;
import io.github.astro.mantis.configuration.invoke.Result;

import static io.github.astro.mantis.common.constant.ServiceType.FaultTolerance.FAIL_RETRY;

/**
 * 容错接口
 */
@ServiceInterface(FAIL_RETRY)
public interface FaultTolerance {

    Result operation(URL url, Invocation invocation);

}
